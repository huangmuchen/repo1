package com.xuecheng.media_processor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HuangMuChen
 * @date: 2020/7/3 16:05
 * @version: V1.0
 * @Description: 媒资视频处理配置类
 */
@Data
@ConfigurationProperties(prefix = "xuecheng.mq")
public class MediaProcessorProperties {
    private String location; // 上传文件的路径
    private String ffmpegPath; // 视频编码软件路径
    private String exchange; // 交换机名称
    private String routingkey; // 路由键名称
    private String queue; // 队列名称
}