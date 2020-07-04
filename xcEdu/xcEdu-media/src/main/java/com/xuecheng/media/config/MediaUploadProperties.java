package com.xuecheng.media.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HuangMuChen
 * @date: 2020/7/3 16:05
 * @version: V1.0
 * @Description: 媒资文件上传配置类
 */
@Data
@ConfigurationProperties(prefix = "media.upload")
public class MediaUploadProperties {
    private String location; // 上传文件的路径
    private String exchange; // 交换机名称
    private String routingkey; // 路由键名称
}