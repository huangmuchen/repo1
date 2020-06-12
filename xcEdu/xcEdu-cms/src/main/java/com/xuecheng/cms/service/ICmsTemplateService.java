package com.xuecheng.cms.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsTemplate;
import com.xuecheng.model.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.model.domain.cms.response.CmsTemplateResult;
import com.xuecheng.model.domain.cms.response.CmsUploadResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 23:54
 * @version: V1.0
 * @Description: 页面模板业务层接口
 */
public interface ICmsTemplateService {

    /**
     * 查询模板列表
     *
     * @return
     */
    QueryResponseResult findCmsTemplateList();

    /**
     * 分页查询模板列表
     *
     * @param page
     * @param size
     * @param queryTemplateRequest
     * @return
     */
    QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest);

    /**
     * 模板文件的上传(新增时)
     *
     * @param file
     * @return
     */
    CmsUploadResult uploadTemplateFile(MultipartFile file);

    /**
     * 模板文件上传（修改时）
     *
     * @param templateFileId
     * @param file
     * @return
     */
    CmsUploadResult editUploadTemplateFile(String templateFileId, MultipartFile file);

    /**
     * 删除刚刚上传的模板文件
     *
     * @param templateFileId
     * @return
     */
    CmsUploadResult deleteTemplateFile(String templateFileId);

    /**
     * 新增模板
     *
     * @param cmsTemplate
     * @return
     */
    CmsTemplateResult add(CmsTemplate cmsTemplate);

    /**
     * 删除模板（及其模板文件）
     *
     * @param templateId
     * @return
     */
    ResponseResult deleteTemplate(String templateId);

    /**
     * 根据模板id查询模板
     *
     * @param templateId
     * @return
     */
    CmsTemplate findTemplateById(String templateId);

    /**
     * 修改模板信息
     *
     * @param templateId
     * @param cmsTemplate
     * @return
     */
    CmsTemplateResult updateTemplate(String templateId, CmsTemplate cmsTemplate);
}