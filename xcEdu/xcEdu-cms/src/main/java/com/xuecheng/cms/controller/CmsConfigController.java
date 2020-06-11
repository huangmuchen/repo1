package com.xuecheng.cms.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.cms.service.ICmsConfigService;
import com.xuecheng.model.domain.cms.CmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/6/10 21:32
 * @version: V1.0
 * @Description: cms数据模型控制层
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    private ICmsConfigService cmsConfigService;

    /**
     * 根据id查询CMS配置信息
     *
     * @param id 主键
     * @return 数据模型
     */
    @Override
    @GetMapping("/getModel/{id}")
    public CmsConfig getModel(@PathVariable("id") String id) {
        // 调用service进行查询，并返回查询结果
        return this.cmsConfigService.getModel(id);
    }
}