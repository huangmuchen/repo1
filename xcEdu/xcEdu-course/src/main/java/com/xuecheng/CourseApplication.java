package com.xuecheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: HuangMuChen
 * @date: 2020/6/17 9:44
 * @version: V1.0
 * @Description: 课程管理服务启动器
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // FeignClient开启，扫描@FeignClient生成代理对象
@MapperScan("com.xuecheng.course.dao.mapper") // mapper接口的包扫描
public class CourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }
}