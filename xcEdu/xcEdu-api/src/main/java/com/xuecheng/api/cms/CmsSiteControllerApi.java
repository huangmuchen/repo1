package com.xuecheng.api.cms;

import com.xuecheng.common.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 20:30
 * @version: V1.0
 * @Description: CmsSite相关的对外暴露的接口：在CMS服务工程编写Controller类实现此接口
 */
@Api(value = "cms站点管理", description = "cms站点管理接口，提供站点的增、删、改、查", tags = "CmsSiteApi")
public interface CmsSiteControllerApi {

    /**
     * 查询站点模型列表
     *
     * @return
     */
    @ApiOperation("查询站点模型")
    QueryResponseResult findCmsSiteList();
}