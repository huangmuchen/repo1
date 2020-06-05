package com.xuecheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author: HuangMuChen
 * @date: 2020/6/5 15:33
 * @version: V1.0
 * @Description: 网关zuul启动器
 */
@EnableZuulProxy  // 开启Zuul的网关功能
@SpringBootApplication
@EnableDiscoveryClient // 开启Eureka客户端发现功能
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}