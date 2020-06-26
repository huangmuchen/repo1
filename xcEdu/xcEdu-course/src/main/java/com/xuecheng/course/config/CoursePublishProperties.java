package com.xuecheng.course.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: HuangMuChen
 * @date: 2020/6/27 0:59
 * @version: V1.0
 * @Description: 课程预览/发布配置类
 */
@Data
@ConfigurationProperties(prefix = "course.publish")
public class CoursePublishProperties {
    private String siteId; // 站点id
    private String templateId; // 模板id
    private String previewUrlPrefix; // 课程预览url前缀
    private String pageWebPath; // 页面web路径
    private String pagePhysicalPath; // 页面物理路径
    private String dataUrlPrefix; // 数据模型url前缀
}