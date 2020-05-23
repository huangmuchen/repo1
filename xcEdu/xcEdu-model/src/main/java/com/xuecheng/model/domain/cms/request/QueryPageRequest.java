package com.xuecheng.model.domain.cms.request;

import com.xuecheng.common.model.request.RequestData;
import lombok.Data;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 23:01
 * @version: V1.0
 * @Description: 请求模型：封装页面查询条件
 */
@Data
public class QueryPageRequest extends RequestData {
    private String siteId; // 站点id
    private String pageId; // 页面ID
    private String pageName; // 页面名称
    private String pageAliase; // 别名
    private String templateId; // 模版id
}