package com.xuecheng.cms.controller;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.cms.service.ICmsSiteService;
import com.xuecheng.common.model.response.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 20:36
 * @version: V1.0
 * @Description: 站点模型控制层
 */
@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements CmsSiteControllerApi {
    @Autowired
    private ICmsSiteService cmsSiteService;

    /**
     * 查询站点模型列表
     *
     * @return
     */
    @Override
    @GetMapping("/list")
    public QueryResponseResult findCmsSiteList() {
        return this.cmsSiteService.findCmsSiteList();
    }
}