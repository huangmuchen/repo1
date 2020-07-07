package com.xuecheng.api.media;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.media.request.QueryMediaFileRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/7/7 10:43
 * @version: V1.0
 * @Description: 媒资文件对外暴露的接口：在Media服务工程编写Controller类实现此接口
 */
@Api(value = "媒资文件管理", description = "媒资文件管理接口，提供媒资文件的的查询、修改、删除和处理", tags = "MediaFileApi")
public interface MediaFileControllerApi {

    @ApiOperation("分页查询媒资文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "queryMediaFileRequest", value = "MediaFile查询条件", paramType = "body", required = false, dataTypeClass = QueryMediaFileRequest.class)
    })
    QueryResponseResult findMediaFileList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);
}