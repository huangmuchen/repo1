package com.xuecheng.filesystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/22 21:25
 * @version: V1.0
 * @Description: 文件上传配置类
 */
@Data
@ConfigurationProperties(prefix = "xuecheng.upload")
public class UploadProperties {
    private List<String> suffixes; // 允许的文件类型
}