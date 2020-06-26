package com.xuecheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: HuangMuChen
 * @date: 2020/5/26 21:12
 * @version: V1.0
 * @Description: 注册中心启动器
 */
@EnableEurekaServer // 标识这是一个Eureka服务
@SpringBootApplication
public class RegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistryApplication.class, args);
    }
}