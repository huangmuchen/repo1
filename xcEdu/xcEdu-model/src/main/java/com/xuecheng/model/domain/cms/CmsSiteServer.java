package com.xuecheng.model.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
@Document(collection = "cms_site_server")
public class CmsSiteServer {
    @Id // 主键
    private String serverId; // 服务器ID
    private String siteId; // 站点id
    private String ip; // 服务器IP
    private String port; // 端口
    private String webPath; // 访问地址
    private String serverName; // 服务器名称（代理、静态、动态、CDN）
    private String uploadPath; // 资源发布地址（完整的HTTP接口）
    private String useType; // 使用类型（测试、生产）
}