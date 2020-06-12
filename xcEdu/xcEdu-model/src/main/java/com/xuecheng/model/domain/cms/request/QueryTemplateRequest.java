package com.xuecheng.model.domain.cms.request;

import com.xuecheng.common.model.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 23:01
 * @version: V1.0
 * @Description: 请求模型：封装页面查询条件
 */
@Data
@ApiModel(value = "QueryTemplateRequest", description = "封装查询条件")
public class QueryTemplateRequest extends RequestData {
    @ApiModelProperty("站点id")
    private String siteId;
    @ApiModelProperty("模板名称")
    private String templateName;
}