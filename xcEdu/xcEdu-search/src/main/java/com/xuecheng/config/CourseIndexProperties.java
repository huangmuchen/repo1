package com.xuecheng.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HuangMuChen
 * @date: 2020/7/1 0:41
 * @version: V1.0
 * @Description: 课程索引配置类
 */
@Data
@ConfigurationProperties(prefix = "xuecheng.course")
public class CourseIndexProperties {
    private String index; // 课程索引库
    private String type; // 索引类型
    private String source_field; // 源字段
}