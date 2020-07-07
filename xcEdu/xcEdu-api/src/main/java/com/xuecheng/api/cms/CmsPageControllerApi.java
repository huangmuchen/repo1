package com.xuecheng.api.cms;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import com.xuecheng.model.domain.cms.response.CmsPageResult;
import com.xuecheng.model.domain.cms.response.CmsPublishPageResult;
import io.swagger.annotations.*;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 23:08
 * @version: V1.0
 * @Description: CmsPage相关的对外暴露的接口：在CMS服务工程编写Controller类实现此接口
 */
@Api(value = "cms页面管理", description = "cms页面管理接口，提供页面的增、删、改、查", tags = "CmsPageApi")
public interface CmsPageControllerApi {

    /**
     * 分页查询CmsPage
     *
     * @param page             页码
     * @param size             每页显示的条数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "queryPageRequest", value = "CmsPage查询条件", paramType = "body", required = false, dataTypeClass = QueryPageRequest.class)
    })
    QueryResponseResult findCmsPageList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 页面添加
     *
     * @param cmsPage
     * @return
     */
    @ApiOperation("页面添加")
    @ApiImplicitParam(name = "cmsPage", value = "页面信息", paramType = "body", required = true, dataTypeClass = CmsPage.class)
    CmsPageResult add(CmsPage cmsPage);

    /**
     * 根据pageId查询页面
     *
     * @param pageId
     * @return
     */
    @ApiOperation("查询某个页面")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, paramType = "path", dataType = "String")
    CmsPage findByPageId(String pageId);

    /**
     * 页面修改
     *
     * @param pageId
     * @param cmsPage
     * @return
     */
    @ApiOperation("页面修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageId", value = "页面id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "cmsPage", value = "页面信息", paramType = "body", required = true, dataTypeClass = CmsPage.class)
    })
    CmsPageResult edit(String pageId, CmsPage cmsPage);

    /**
     * 根据pageId删除页面
     *
     * @param pageId
     * @return
     */
    @ApiOperation("页面删除")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, paramType = "path", dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 10000, message = "操作成功"),
            @ApiResponse(code = 11111, message = "操作失败")
    })
    ResponseResult del(String pageId);

    /**
     * 根据pageId发布页面
     *
     * @param pageId
     * @return
     */
    @ApiOperation("页面发布")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, paramType = "path", dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 10000, message = "操作成功"),
            @ApiResponse(code = 11111, message = "操作失败")
    })
    ResponseResult release(String pageId);

    /**
     * 根据pageId撤销页面发布
     *
     * @param pageId
     * @return
     */
    @ApiOperation("撤销页面发布")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, paramType = "path", dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 10000, message = "操作成功"),
            @ApiResponse(code = 11111, message = "操作失败")
    })
    ResponseResult rollBack(String pageId);

    /**
     * 添加Page（课程详情页），提供给课程服务调用，如果已经有了则更新
     *
     * @param cmsPage
     * @return
     */
    @ApiOperation("添加Page，提供给课程服务调用，如果已经有了则更新")
    @ApiImplicitParam(name = "cmsPage", value = "页面信息", paramType = "body", required = true, dataTypeClass = CmsPage.class)
    CmsPageResult saveCoursePage(CmsPage cmsPage);

    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    @ApiOperation("一键发布页面，主要为其他服务[课程详情页、教师信息页、教育机构页、各种统计信息页...]调用")
    @ApiImplicitParam(name = "cmsPage", value = "页面信息", paramType = "body", required = true, dataTypeClass = CmsPage.class)
    CmsPublishPageResult publishPageQuick(CmsPage cmsPage);
}