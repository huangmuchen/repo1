package com.xuecheng.auth.test;

import com.xuecheng.common.client.XcServiceList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/7/11 21:50
 * @version: V1.0
 * @Description: 用户认证测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAuth {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void testAuth() {
        // 从eureka中获取认证服务的一个实例的地址(因为spring security在认证服务中)
        ServiceInstance instance = loadBalancerClient.choose(XcServiceList.XCEDU_AUTH);
        // 拼接申请令牌的url
        String authUrl = instance.getUri() + "/auth/oauth/token";

        // 配置请求头
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        // 加密应用名密码，并放在header中请求服务端
        header.add("Authorization", "Basic " + httpbasic("XcWebApp", "XcWebApp"));

        // 配置body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", "itcast");
        body.add("password", "123");

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
        ResponseEntity<Map> responseEntity = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);
        // 令牌信息
        System.out.println(responseEntity.getBody());
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