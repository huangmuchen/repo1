package com.xuecheng.media_processor.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: HuangMuChen
 * @date: 2020/7/7 11:03
 * @version: V1.0
 * @Description: Rabbitmq配合类
 */
@Configuration
public class RabbitmqConfig {
    public static final int DEFAULT_CONCURRENT = 10;  // 消费者并发数量

    @Bean("myContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        // 创建监听工厂对象
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 初始化消费者数量
        factory.setConcurrentConsumers(DEFAULT_CONCURRENT);
        // 最大消费者数量
        factory.setMaxConcurrentConsumers(DEFAULT_CONCURRENT);
        configurer.configure(factory, connectionFactory);
        // 返回工厂对象
        return factory;
    }
}