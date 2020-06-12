package com.xuecheng.cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.cms.service.ICmsTemplateService;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsTemplate;
import com.xuecheng.model.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.model.domain.cms.response.CmsTemplateResult;
import com.xuecheng.model.domain.cms.response.CmsUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 23:48
 * @version: V1.0
 * @Description: cms模板控制层
 */
@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {
    @Autowired
    private ICmsTemplateService cmsTemplateService;

    /**
     * 查询模板列表
     *
     * @return
     */
    @Override
    @GetMapping("/list")
    public QueryResponseResult findCmsTemplateList() {
        // 调用service进行查询，并返回查询结果
        return this.cmsTemplateService.findCmsTemplateList();
    }

    /**
     * 分页查询模板列表
     *
     * @param page
     * @param size
     * @param queryTemplateRequest
     * @return
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable int page, @PathVariable int size, QueryTemplateRequest queryTemplateRequest) {
        // 调用Service层进行查询,并返回结果
        return this.cmsTemplateService.findList(page, size, queryTemplateRequest);
    }

    /**
     * 模板文件的上传(新增时)
     *
     * @param file
     * @return
     */
    @Override
    @PostMapping("/uploadTemplateFile")
    public CmsUploadResult uploadTemplateFile(@RequestParam(value = "file") MultipartFile file) {
        // 调用Service层进行文件上传,并返回结果
        return this.cmsTemplateService.uploadTemplateFile(file);
    }

    /**
     * 模板文件上传（修改时）
     *
     * @param templateFileId
     * @param file
     * @return
     */
    @Override
    @PostMapping("/editUploadTemplateFile")
    public CmsUploadResult editUploadTemplateFile(@RequestParam("templateFileId") String templateFileId, @RequestParam(value = "file") MultipartFile file) {
        return this.cmsTemplateService.editUploadTemplateFile(templateFileId, file);
    }

    /**
     * 删除刚刚上传的模板文件
     *
     * @param templateFileId
     * @return
     */
    @Override
    @GetMapping("/deleteTemplateFile/{templateFileId}")
    public CmsUploadResult deleteTemplateFile(@PathVariable String templateFileId) {
        return this.cmsTemplateService.deleteTemplateFile(templateFileId);
    }

    /**
     * 新增模板
     *
     * @param cmsTemplate
     * @return
     */
    @Override
    @PostMapping("/add")
    public CmsTemplateResult add(@RequestBody CmsTemplate cmsTemplate) {
        return this.cmsTemplateService.add(cmsTemplate);
    }

    /**
     * 删除模板（及其模板文件）
     *
     * @param templateId
     * @return
     */
    @Override
    @DeleteMapping("/del/{templateId}")
    public ResponseResult deleteTemplate(@PathVariable("templateId") String templateId) {
        return this.cmsTemplateService.deleteTemplate(templateId);
    }

    /**
     * 根据模板id查询模板
     *
     * @param templateId
     * @return
     */
    @Override
    @GetMapping("/findTemplateById/{templateId}")
    public CmsTemplate findTemplateById(@PathVariable("templateId") String templateId) {
        return this.cmsTemplateService.findTemplateById(templateId);
    }

    /**
     * 修改模板信息
     *
     * @param templateId
     * @param cmsTemplate
     * @return
     */
    @Override
    @PutMapping("/edit/{templateId}")
    public CmsTemplateResult updateTemplate(@PathVariable("templateId") String templateId, @RequestBody CmsTemplate cmsTemplate) {
        return this.cmsTemplateService.updateTemplate(templateId, cmsTemplate);
    }
}