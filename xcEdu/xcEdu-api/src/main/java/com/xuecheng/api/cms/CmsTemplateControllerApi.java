package com.xuecheng.api.cms;

import com.xuecheng.common.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 23:48
 * @version: V1.0
 * @Description: 和CmsTemplate相关的对外暴露的接口：在CMS服务工程编写Controller类实现此接口
 */
@Api(value = "cms模板管理接口", description = "cms模板管理接口，提供模板的增、删、改、查",tags = "CmsTemplateApi")
public interface CmsTemplateControllerApi {
    /**
     * 查询模板列表
     *
     * @return
     */
    @ApiOperation("查询模板列表")
    QueryResponseResult findCmsTemplateList();
}
