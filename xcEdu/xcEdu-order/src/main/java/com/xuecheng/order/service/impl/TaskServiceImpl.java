package com.xuecheng.order.service.impl;

import com.github.pagehelper.Page;
import com.xuecheng.model.domain.task.XcTask;
import com.xuecheng.model.domain.task.XcTaskHis;
import com.xuecheng.order.dao.XcTaskHisRepository;
import com.xuecheng.order.dao.XcTaskRepository;
import com.xuecheng.order.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: HuangMuChen
 * @date: 2020/7/17 8:43
 * @version: V1.0
 * @Description: 定时任务业务层实现类
 */
@Service
@Slf4j
public class TaskServiceImpl implements ITaskService, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private XcTaskRepository taskRepository;
    @Autowired
    private XcTaskHisRepository taskHisRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 分页查询某个时间之前的N条记录
     *
     * @param size
     * @param time
     * @return
     */
    @Override
    public List<XcTask> getTaskList(int size, Date time) {
        // 设置分页参数
        PageRequest pageRequest = PageRequest.of(0, size);
        // 调用dao进行查询
        Page<XcTask> page = this.taskRepository.findByUpdateTimeBefore(pageRequest, time);
        // 返回查询结果集
        return page.getResult();
    }

    /**
     * 乐观锁控制：防止发送多条重复消息
     *
     * @param id
     * @param version
     * @return
     */
    @Transactional
    public int versionControl(String id, int version) {
        // 如果根据任务id和当前版本号能成功更新任务版本，说明该任务未被其他定时任务修改
        return this.taskRepository.updateTaskVersion(id, version);
    }

    /**
     * 删除待处理任务，并将任务移至历史任务表中
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHisAndDeleteTask(String id) {
        // 查询待处理任务
        Optional<XcTask> optional = this.taskRepository.findById(id);
        // 判断
        if (optional.isPresent()) {
            // 取出任务
            XcTask task = optional.get();
            // 设置删除时间
            task.setDeleteTime(new Date());
            // 创建一个历史任务对象
            XcTaskHis taskHis = new XcTaskHis();
            // 属性拷贝
            BeanUtils.copyProperties(task, taskHis);
            // 保存到历史任务表
            this.taskHisRepository.save(taskHis);
            // 删除待处理任务
            this.taskRepository.delete(task);
        }
    }

    /**
     * 发送自动选课消息给mq
     *
     * @param task
     * @param exchange
     * @param routingkey
     */
    public void sendChooseCourseMsg(XcTask task, String exchange, String routingkey) {
        // 查询任务
        Optional<XcTask> optional = this.taskRepository.findById(task.getId());
        // 校验通过，则发送mq消息
        if (optional.isPresent()) {
            XcTask xcTask = optional.get();
            // 开启强制委托模式
            this.rabbitTemplate.setMandatory(true);
            // 设置消息发送给交换机的回调
            this.rabbitTemplate.setConfirmCallback(this);
            // 设置消息从交换机发送给队列的回调
            this.rabbitTemplate.setReturnCallback(this);
            // 关联数据
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            // 发送消息给交换机(如果不指定，将发给.yml文件中默认的交换机))，并将站点id作为routingKey
            this.rabbitTemplate.convertAndSend(exchange, routingkey, xcTask, correlationData);
            // 发送完成，修改任务更新时间
            xcTask.setUpdateTime(new Date());
            this.taskRepository.save(xcTask);
        }
    }

    /**
     * 消息是否成功到达交换机
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            // 消息成功到达交换机
            log.info("回调消息ID为：{}，消息成功发送到交换机！", correlationData.getId());
        } else {
            // 消息到达交换机失败
            log.error("回调消息ID为：{}，消发送到交换机失败！原因：[{}]", correlationData.getId(), cause);
        }
    }

    /**
     * 消息是否成功从交换机到达队列：成功，则returnedMessage方法不会执行，失败，returnedMessage方法会执行
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("发送消息：{}，错误码：{}，错误原因：{}，exchange：{}，routingKey：{}", new String(message.getBody(), StandardCharsets.UTF_8), replyCode, replyText, exchange, routingKey);
    }
}