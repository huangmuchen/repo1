package com.xuecheng.media_processor.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xuecheng.media_processor.service.IMediaProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/7/5 0:36
 * @version: V1.0
 * @Description: 监听RabbitMQ中关于视频处理的消息
 */
@Slf4j
@Component
public class MediaProcessListener {
    @Autowired
    private IMediaProcessService mediaProcessorService;

    /**
     * 监听视频处理的消息
     *
     * @param msg
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${xuecheng.mq.queue}", durable = "true"),
            exchange = @Exchange(value = "${xuecheng.mq.exchange}", ignoreDeclarationExceptions = "true", type = ExchangeTypes.DIRECT),
            key = {"${xuecheng.mq.routingKey}"}))
    public void listenMediaProcessor(String msg, Channel channel, Message message) throws IOException {
        try {
            // 打印监听到的信息
            log.info("监听到视频处理信息：{}", msg);
            // 解析消息：将JSON格式的msg转为Map对象
            Map map = JSON.parseObject(msg, Map.class);
            // 获取页面id
            String mediaId = (String) map.get("mediaId");
            // 根据mediaId，进行视频处理
            this.mediaProcessorService.processMedia(mediaId);
            // 消息处理确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            // 打印日志
            log.info("消息：{}消费成功", msg);
        } catch (Exception e) {
            // 消息处理失败后的处理逻辑
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息{}消费失败，已经回滚过，拒绝接收消息，失败原因：{}", msg, e.getMessage());
                // 拒绝消息，并且不再重新进入队列
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("消息{}消费失败，即将返回队列重新处理，失败原因：{}", msg, e.getMessage());
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}