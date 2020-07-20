package com.xuecheng.learning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HuangMuChen
 * @date: 2020/7/20 12:48
 * @version: V1.0
 * @Description: 课程学习配置类
 */
@Data
@ConfigurationProperties(prefix = "xuecheng.mq")
public class LearningProperties {
    private String exchange; // 交换机名称
    private String queue_add; // 添加选课队列
    private String routingKey_add; // 添加选课路由键
    private String queue_finish; // 选课添加完成队列
    private String routingKey_finish; // 选课添加完成路由键
}