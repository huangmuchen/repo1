package com.xuecheng.model.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 页面信息
 */
@Data
@ToString
@Document(collection = "cms_page") // 用于标记此实体类是mongodb集合映射类
public class CmsPage {
    @Id // 主键
    private String pageId; // 页面ID
    private String siteId; // 站点ID
    private String pageName; // 页面名称
    private String pageAliase; // 别名
    private String templateId; // 模版id
    private String pageWebPath; // 访问地址
    private String pageParameter; // 参数
    private String pagePhysicalPath;  // 物理路径
    private String pageType; // 类型（静态/动态）
    private String pageTemplate; // 页面模版
    private String pageHtml; // 页面静态化内容
    private String pageStatus; // 状态
    private Date pageCreateTime; // 创建时间
    private List<CmsPageParam> pageParams; // 参数列表,暂时不用
    private String htmlFileId; // 静态文件Id
    private String dataUrl; // 数据Url
}