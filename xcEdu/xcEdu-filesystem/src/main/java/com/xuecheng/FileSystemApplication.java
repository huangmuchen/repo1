package com.xuecheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: HuangMuChen
 * @date: 2020/6/23 8:48
 * @version: V1.0
 * @Description: 文件系统启动器
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FileSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileSystemApplication.class, args);
    }
}