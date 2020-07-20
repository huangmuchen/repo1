package com.xuecheng.learning.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.learning.service.ILearningService;
import com.xuecheng.model.domain.task.XcTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/7/17 11:39
 * @version: V1.0
 * @Description: 监听自动选课消息
 */
@Component
@Slf4j
public class ChooseCourseTaskListener {
    @Autowired
    private ILearningService learningService;

    /**
     * 监听自动选课消息
     *
     * @param task
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${xuecheng.mq.queue_add}", durable = "true"),
            exchange = @Exchange(value = "${xuecheng.mq.exchange}", ignoreDeclarationExceptions = "true", type = ExchangeTypes.DIRECT),
            key = {"${xuecheng.mq.routingKey_add}"})
    )
    public void listenChooseCourse(XcTask task, Channel channel, Message message) throws IOException {
        try {
            // 打印监听到的信息
            log.info("监听到自动选课信息：{}", task);
            // 判断
            if (task != null && StringUtils.isNotBlank(task.getRequestBody())) {
                // 取出选课内容
                String requestBody = task.getRequestBody();
                // 转成Map对象
                Map map = JSON.parseObject(requestBody, Map.class);
                String userId = (String) map.get("userId");
                String courseId = (String) map.get("courseId");
                String valid = (String) map.get("valid"); // 课程有效性：永久有效(204001)，指定时间段(204002)
                Date startTime = null;
                Date endTime = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (map.get("startTime") != null) {
                    startTime = dateFormat.parse((String) map.get("startTime"));
                }
                if (map.get("endTime") != null) {
                    endTime = dateFormat.parse((String) map.get("endTime"));
                }
                // 添加选课
                ResponseResult responseResult = this.learningService.addcourse(userId, courseId, valid, startTime, endTime, task);
                // 判断是否添加成功
                if (responseResult.isSuccess()) {
                    // 发送选课添加成功消息给mq
                    this.learningService.sendFinishChooseCourseMsg(task);
                }
            }
            // 消息处理确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            // 打印日志
            log.info("自动选课消息：{}消费成功", task);
        } catch (Exception e) {
            // 消息处理失败后的处理逻辑
            if (message.getMessageProperties().getRedelivered()) {
                log.error("自动选课消息{}消费失败，已经回滚过，拒绝接收消息，失败原因：{}", task, e.getMessage());
                // 拒绝消息，并且不再重新进入队列
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("自动选课消息{}消费失败，即将返回队列重新处理，失败原因：{}", task, e.getMessage());
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}
