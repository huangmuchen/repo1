package com.xuecheng.cms.service;

import com.xuecheng.common.model.response.QueryResponseResult;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 20:37
 * @version: V1.0
 * @Description: 站点模型业务层接口
 */
public interface ICmsSiteService {

    /**
     * 查询站点模型列表
     *
     * @return
     */
    QueryResponseResult findCmsSiteList();
}