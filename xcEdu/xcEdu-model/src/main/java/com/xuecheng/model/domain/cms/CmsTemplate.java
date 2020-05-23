package com.xuecheng.model.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 页面模板
 */
@Data
@ToString
@Document(collection = "cms_template")
public class CmsTemplate {
    @Id // 主键
    private String templateId; // 模版ID
    private String siteId; // 站点ID
    private String templateName; // 模版名称
    private String templateParameter; // 模版参数
    private String templateFileId; // 模版文件Id
}