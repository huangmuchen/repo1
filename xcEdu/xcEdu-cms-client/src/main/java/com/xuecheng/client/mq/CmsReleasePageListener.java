package com.xuecheng.client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.client.service.ICmsPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/6/15 21:44
 * @version: V1.0
 * @Description: 监听RabbitMQ中关于页面发布的消息
 */
@Slf4j
@Component
public class CmsReleasePageListener {
    @Autowired
    private ICmsPageService cmsPageService;

    /**
     * 监听页面发布的消息
     *
     * @param msg 页面发布的消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${xuecheng.mq.queue1}", durable = "true"),
            exchange = @Exchange(value = "${xuecheng.mq.exchange}", ignoreDeclarationExceptions = "true", type = ExchangeTypes.DIRECT),
            key = {"${xuecheng.mq.routingKey1}"}))
    public void listenReleasePage(String msg, Channel channel, Message message) throws IOException {
        try {
            // 打印监听到的信息
            log.info("监听到页面发布信息：{}", msg);
            // 解析消息：将JSON格式的msg转为Map对象
            Map map = JSON.parseObject(msg, Map.class);
            // 获取页面id
            String pageId = (String) map.get("pageId");
            // 获取发布类型
            String type = (String) map.get("type");
            // deliveryTag是消息传送的次数，我这里是为了让消息队列的第一个消息到达的时候抛出异常，处理异常让消息重新回到队列，然后再次抛出异常，处理异常拒绝让消息重回队列
            if (message.getMessageProperties().getDeliveryTag() == 1 || message.getMessageProperties().getDeliveryTag() == 2) {
                // 模拟一个算术异常
                // System.out.println(1 / 0);
            }
            // 根据pageId，从GridFS中获取html保存到服务器指定路径上
            this.cmsPageService.savePageToServerPath(pageId, type);
            /*
             * 消息处理成功后，对消息进行确认
             * 参数明细：
             *   1、deliveryTag：消息id，mq在channel中用来标识消息的id，可用于确认消息已接收
             *   2、multipl：是否批量确认消息，true -> 将一次性ack所有小于deliveryTag的消息;  false -> 一次只ack一条消息
             */
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
                /*
                 * 设置消息重新回到队列处理
                 * 参数明细：
                 *   1、deliveryTag：消息id，mq在channel中用来标识消息的id，可用于确认消息已接收
                 *   2、multipl：是否批量确认消息，true -> 将一次性ack所有小于deliveryTag的消息;  false -> 一次只ack一条消息
                 *   3、requeue：被拒绝的消息是否重新入队列
                 */
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}