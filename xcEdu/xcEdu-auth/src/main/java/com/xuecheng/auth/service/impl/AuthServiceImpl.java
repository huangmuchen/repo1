package com.xuecheng.auth.service.impl;

import com.xuecheng.auth.config.AuthProperties;
import com.xuecheng.auth.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:10
 * @version: V1.0
 * @Description: 用户鉴权业务层实现类
 */
@EnableConfigurationProperties(AuthProperties.class)
@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private AuthProperties prop;
    @Autowired
    private StringRedisTemplate redisTemplate;
}