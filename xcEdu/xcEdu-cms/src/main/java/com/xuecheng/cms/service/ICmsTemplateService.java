package com.xuecheng.cms.service;

import com.xuecheng.common.model.response.QueryResponseResult;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 23:54
 * @version: V1.0
 * @Description: 页面模板业务层接口
 */
public interface ICmsTemplateService {

    /**
     * 查询页面模板列表
     *
     * @return
     */
    QueryResponseResult findCmsTemplateList();
}