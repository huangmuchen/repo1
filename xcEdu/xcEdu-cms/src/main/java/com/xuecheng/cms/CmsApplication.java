package com.xuecheng.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: HuangMuChen
 * @date: 2020/5/24 0:18
 * @version: V1.0
 * @Description: cms服务启动器
 */
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.xuecheng.model.domain.cms") // 扫描实体类
@ComponentScan(basePackages = {"com.xuecheng.api"}) // 扫描api接口
@ComponentScan(basePackages = {"com.xuecheng.cms"}) // 扫描本项目下的所有类，可不写，SpringBoot默认的扫描路径就是启动类当前的包和子包
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}