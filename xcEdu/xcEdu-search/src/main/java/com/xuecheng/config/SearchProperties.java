package com.xuecheng.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HuangMuChen
 * @date: 2020/7/1 0:41
 * @version: V1.0
 * @Description: ES配置类：ip、port、索引、类型、源字段
 */
@Data
@ConfigurationProperties(prefix = "xuecheng.elasticsearch")
public class SearchProperties {
    private String hostList; // es结点访问地址
    private String courseIndex; // 课程索引库
    private String courseType; // 索引类型
    private String courseSourceFields; // 课程源字段
    private String mediaIndex; // 媒资索引库
    private String mediaType; // 索引类型
    private String mediaSourceFields; // 媒资源字段
}