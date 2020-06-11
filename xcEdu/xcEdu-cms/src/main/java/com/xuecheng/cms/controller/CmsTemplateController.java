package com.xuecheng.cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.cms.service.ICmsTemplateService;
import com.xuecheng.common.model.response.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return cmsTemplateService.findCmsTemplateList();
    }
}