package com.xuecheng.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HuangMuChen
 * @date: 2019/11/9 19:29
 * @version: V1.0
 * @Description: filter配置类
 */
@Data
@ConfigurationProperties(prefix = "xuecheng.filter")
public class FilterProperties {
    private String cookieName;
    private String prefixKey;
}