package com.xuecheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: HuangMuChen
 * @date: 2020/7/3 15:11
 * @version: V1.0
 * @Description: 媒资启动器
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class, args);
    }
}