package com.xuecheng.order.mq;

import com.rabbitmq.client.Channel;
import com.xuecheng.model.domain.task.XcTask;
import com.xuecheng.order.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/7/17 8:14
 * @version: V1.0
 * @Description: 选课任务发送和监听
 * <p>
 * 1. @Scheduled(fixedRate = 5000) // 上次执行开始时间后5秒执行
 * 2. @Scheduled(fixedDelay = 5000) // 上次执行完毕后5秒执行
 * 3. @Scheduled(initialDelay=3000, fixedRate=5000) // 第一次延迟3秒，以后每隔5秒执行一次
 */
@Component
@Slf4j
public class ChooseCourseTaskListener {
    @Autowired
    private ITaskService taskService;
    // 任务数量
    private final static int TASK_NUM = 1;

    /**
     * 定时扫描任务表，并向mq发送自动选课消息
     */
    @Scheduled(cron = "0/3 * * * * *") // 每隔3秒扫描任务表，并发送选课消息(生产环境，需配置更长时间，比如1分钟： 0 0/1 * * * *)
    public void sendChooseCourseMsg() {
        // 创建一个日历对象
        Calendar calendar = new GregorianCalendar();
        // 设置日历时间为当前时间
        calendar.setTime(new Date());
        // 取一分钟之前的任务(减一分钟)
        calendar.add(GregorianCalendar.MINUTE, -1);
        // 生成任务时间
        Date time = calendar.getTime();
        // 调用service层获取1分钟之前的10条任务
        List<XcTask> taskList = this.taskService.getTaskList(TASK_NUM, time);
        // 遍历任务列表
        taskList.forEach(task -> {
            // 乐观锁：更新成功返回1，否则返回0
            if (this.taskService.versionControl(task.getId(), task.getVersion()) > 0) {
                // 向mq发送自动选课消息
                this.taskService.sendChooseCourseMsg(task, task.getMqExchange(), task.getMqRoutingkey());
                // 记录日志
                log.info("ChooseCourseTask send message taskId：{}", task.getId());
            }
        });
    }

    /**
     * 监听选课完成消息
     *
     * @param task
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${xuecheng.mq.queue_finish}", durable = "true"),
            exchange = @Exchange(value = "${xuecheng.mq.exchange}", ignoreDeclarationExceptions = "true", type = ExchangeTypes.DIRECT),
            key = {"${xuecheng.mq.routingKey_finish}"})
    )
    public void listenFinishChooseCourse(XcTask task, Channel channel, Message message) throws IOException {
        try {
            // 打印监听到的信息
            log.info("监听到选课完成信息：{}", task);
            // 判断
            if (task != null && StringUtils.isNotBlank(task.getId())) {
                // 调用service层删除待处理任务，并将任务移至历史任务表中
                this.taskService.saveHisAndDeleteTask(task.getId());
            }
            // 消息处理确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            // 打印日志
            log.info("选课完成消息：{}消费成功", task);
        } catch (Exception e) {
            // 消息处理失败后的处理逻辑
            if (message.getMessageProperties().getRedelivered()) {
                log.error("选课完成消息{}消费失败，已经回滚过，拒绝接收消息，失败原因：{}", task, e.getMessage());
                // 拒绝消息，并且不再重新进入队列
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("选课完成消息{}消费失败，即将返回队列重新处理，失败原因：{}", task, e.getMessage());
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}