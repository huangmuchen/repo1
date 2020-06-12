package com.xuecheng.api.cms;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsTemplate;
import com.xuecheng.model.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.model.domain.cms.response.CmsTemplateResult;
import com.xuecheng.model.domain.cms.response.CmsUploadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 23:48
 * @version: V1.0
 * @Description: 和CmsTemplate相关的对外暴露的接口：在CMS服务工程编写Controller类实现此接口
 */
@Api(value = "cms模板管理接口", description = "cms模板管理接口，提供模板的增、删、改、查", tags = "CmsTemplateApi")
public interface CmsTemplateControllerApi {

    /**
     * 查询模板列表
     *
     * @return
     */
    @ApiOperation("查询模板列表")
    QueryResponseResult findCmsTemplateList();

    /**
     * 分页查询模板列表
     *
     * @param page
     * @param size
     * @param queryTemplateRequest
     * @return
     */
    @ApiOperation("分页查询模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest);

    /**
     * 模板文件上传(添加时)
     *
     * @param file
     * @return
     */
    @ApiOperation("模板文件上传(添加时)")
    @ApiImplicitParam(name = "file", value = "上传文件", required = true, paramType = "form", dataType = "file")
    CmsUploadResult uploadTemplateFile(MultipartFile file);

    /**
     * 模板文件上传(修改时)
     *
     * @param templateFileId
     * @param file
     * @return
     */
    @ApiOperation("模板文件上传(修改时)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateFileId", value = "模板文件id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "file", value = "上传文件", required = true, paramType = "form", dataType = "file")
    })
    CmsUploadResult editUploadTemplateFile(String templateFileId, MultipartFile file);

    /**
     * 删除上传的模板文件
     *
     * @param templateFileId
     * @return
     */
    @ApiOperation("删除上传的模板文件")
    @ApiImplicitParam(name = "templateFileId", value = "模板文件id", required = true, paramType = "path", dataType = "String")
    CmsUploadResult deleteTemplateFile(String templateFileId);

    /**
     * 新增模板
     *
     * @param cmsTemplate
     * @return
     */
    @ApiOperation("新增模板")
    CmsTemplateResult add(CmsTemplate cmsTemplate);

    /**
     * 删除模板及其模板文件
     *
     * @param templateId
     * @return
     */
    @ApiOperation("删除模板及其模板文件")
    @ApiImplicitParam(name = "templateId", value = "模板id", required = true, paramType = "path", dataType = "String")
    ResponseResult deleteTemplate(String templateId);

    /**
     * 根据模板Id获取模板信息
     *
     * @param templateId
     * @return
     */
    @ApiOperation("根据模板Id获取模板信息")
    @ApiImplicitParam(name = "templateId", value = "模板id", required = true, paramType = "path", dataType = "String")
    CmsTemplate findTemplateById(String templateId);

    /**
     * 修改模板信息
     *
     * @param templateId
     * @param cmsTemplate
     * @return
     */
    @ApiOperation("修改模板信息")
    @ApiImplicitParam(name = "templateId", value = "模板id", required = true, paramType = "path", dataType = "String")
    CmsTemplateResult updateTemplate(String templateId, CmsTemplate cmsTemplate);
}