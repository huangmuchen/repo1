package com.xuecheng.cms.service;

import com.xuecheng.model.domain.cms.CmsConfig;

/**
 * @author: HuangMuChen
 * @date: 2020/6/10 21:43
 * @version: V1.0
 * @Description: cms数据模型业务层接口
 */
public interface ICmsConfigService {

    /**
     * 根据id查询CMS配置信息
     *
     * @param id 主键
     * @return 数据模型
     */
    CmsConfig getModel(String id);
}