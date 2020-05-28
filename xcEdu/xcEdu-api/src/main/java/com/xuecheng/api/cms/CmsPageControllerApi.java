package com.xuecheng.api.cms;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 23:08
 * @version: V1.0
 * @Description: 和CmsPage相关的对外暴露的接口：在CMS服务工程编写Controller类实现此接口
 */
@Api(value = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查",tags = "CmsPageApi")
public interface CmsPageControllerApi {

    /**
     * 分页查询CmsPage
     *
     * @param page             页码
     * @param size             每页显示的条数
     * @param queryPageRequest 查询条件
     * @return                 页面列表
     */
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
}