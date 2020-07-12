package com.xuecheng.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.auth.config.AuthProperties;
import com.xuecheng.auth.service.IAuthService;
import com.xuecheng.common.client.XcServiceList;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.model.domain.ucenter.ext.AuthToken;
import com.xuecheng.model.domain.ucenter.request.LoginRequest;
import com.xuecheng.model.domain.ucenter.response.AuthCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:10
 * @version: V1.0
 * @Description: 用户鉴权业务层实现类
 */
@EnableConfigurationProperties(AuthProperties.class)
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private AuthProperties prop;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 登录认证
     *
     * @param loginRequest
     * @return
     */
    @Override
    public AuthToken login(LoginRequest loginRequest) {
        // 校验用户名是否为空
        if (StringUtils.isBlank(loginRequest.getUsername())) {
            throw new CustomException(AuthCode.AUTH_USERNAME_NONE);
        }
        // 校验密码是否为空
        if (StringUtils.isBlank(loginRequest.getPassword())) {
            throw new CustomException(AuthCode.AUTH_PASSWORD_NONE);
        }
        // 获取令牌
        AuthToken authToken = appleyToken(loginRequest.getUsername(), loginRequest.getPassword(), prop.getClientId(), prop.getClientSecret());
        // 校验令牌
        if (authToken == null) {
            throw new CustomException(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        // 将令牌保存到redis中
        boolean result = this.saveAuthToken(authToken);
        // 校验保存结果
        if (!result) {
            throw new CustomException(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }
        return authToken;
    }

    /**
     * 保存令牌信息到redis中
     *
     * @param authToken
     * @return
     */
    public boolean saveAuthToken(AuthToken authToken) {
        // redis的key
        String key = prop.getPrefixKey() + authToken.getJti_token();
        // 存储内容
        String value = JSON.toJSONString(authToken);
        // 保存到redis中
        this.redisTemplate.boundValueOps(key).set(value, prop.getTokenValiditySeconds(), TimeUnit.SECONDS);
        // 通过过期时间判断是否存放成功：-2代表保存失败
        Long expire = this.redisTemplate.getExpire(key);
        // 判断
        if (expire != null) {
            return expire > 0;
        }
        return false;
    }

    /**
     * 请求SpringSecurity，通过密码授权模式获取令牌
     *
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    public AuthToken appleyToken(String username, String password, String clientId, String clientSecret) {
        // 从eureka中获取认证服务的一个实例的地址(因为spring security在认证服务中)
        ServiceInstance instance = loadBalancerClient.choose(XcServiceList.XCEDU_AUTH);
        // 拼接申请令牌的地址
        String authUrl = instance.getUri() + "/auth/oauth/token";
        // 配置请求头
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        // 加密应用名密码，并放在header中请求服务端
        header.add("Authorization", "Basic " + httpbasic(clientId, clientSecret));
        // 配置body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", prop.getGrantType());
        body.add("username", username);
        body.add("password", password);
        // 拼装HttpEntity
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
        // 指定RestTemplate遇到400或401错误时，不报错，而是返回结果
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // 当响应的值为400或401时候也要正常响应，不要抛出异常
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        // 执行认证
        ResponseEntity<Map> entity = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);
        // 获取令牌信息
        Map map = entity.getBody();
        // 判断令牌
        if (CollectionUtils.isEmpty(map) || map.get("access_token") == null || map.get("refresh_token") == null || map.get("jti") == null) {
            return null;
        }
        // 创建一个令牌封装对象
        AuthToken authToken = new AuthToken();
        // 封装jwt令牌
        authToken.setJwt_token((String) map.get("access_token"));
        // 封装刷新令牌
        authToken.setRefresh_token((String) map.get("refresh_token"));
        // 封装用户身份令牌
        authToken.setJti_token((String) map.get("jti"));
        // 返回令牌信息
        return authToken;
    }

    /**
     * 退出认证
     *
     * @return
     */
    @Override
    public void logout(String token) {
        // 拼接key
        String key = prop.getPrefixKey() + token;
        // 根据key删除redis中的token
        this.redisTemplate.delete(key);
    }

    /**
     * 封装请求Header
     *
     * @param name
     * @param pwd
     * @return
     */
    public String httpbasic(String name, String pwd) {
        // 拼接客户端ID和客户端密码
        String code = name + ":" + pwd;
        // 进行base64编码
        byte[] encode = Base64Utils.encode(code.getBytes());
        // 返回编码后的字符串
        return new String(encode);
    }
}