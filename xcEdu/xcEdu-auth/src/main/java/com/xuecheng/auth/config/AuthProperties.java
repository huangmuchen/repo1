package com.xuecheng.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HuangMuChen
 * @date: 2020/7/11 21:50
 * @version: V1.0
 * @Description: 用户认证配置类
 */
@Data
@ConfigurationProperties(prefix = "xuecheng.auth")
public class AuthProperties {
    private long tokenValiditySeconds;  // token存储到redis的过期时间，单位：秒
    private String clientId; // 客户端id
    private String clientSecret; // 客户端密码
    private String cookieDomain; // cookie域名
    private int cookieMaxAge; // cookie最大存活时间
    private String cookieName; // cookie名称
    private String grantType; // 授权模式
    private String prefixKey; // redis的key前缀
}