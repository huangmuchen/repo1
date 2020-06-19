package com.xuecheng.api.cms;

import com.xuecheng.model.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/6/10 21:14
 * @version: V1.0
 * @Description: 和CmsConfig相关的对外暴露的接口：在CMS服务工程编写Controller类实现此接口
 */
@Api(value = "cms配置管理接口", description = "cms配置管理接口，提供数据模型的增、删、改、查", tags = "CmsConfigApi")
public interface CmsConfigControllerApi {

    /**
     * 根据id查询CMS配置信息
     *
     * @param id 主键
     * @return 数据模型
     */
    @ApiOperation("根据id查询CMS配置信息")
    @ApiImplicitParam(name = "id", value = "数据模型id", required = true, paramType = "path", dataType = "String")
    CmsConfig getModel(String id);
}