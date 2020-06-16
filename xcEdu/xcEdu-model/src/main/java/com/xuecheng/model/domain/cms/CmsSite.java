package com.xuecheng.model.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 站点模型
 */
@Data
@ToString
@Document(collection = "cms_site")
public class CmsSite {
    @Id // 主键
    private String siteId; // 站点ID
    private String siteName; // 站点名称
    private String siteDomain; // 站点访问地址
    private String sitePort; // 站点端口
    private String siteWebPath; // 站点路径
    private Date siteCreateTime; // 创建时间
    private String sitePhysicalPath;  // 站点物理路径
}