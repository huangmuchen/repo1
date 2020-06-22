package com.xuecheng.cms.service;

import com.xuecheng.model.domain.system.SysDictionary;

/**
 * @author: HuangMuChen
 * @date: 2020/6/22 13:27
 * @version: V1.0
 * @Description: 数据字典业务层接口
 */
public interface ISysDictionaryService {

    /**
     * 根据dType获取数据字典
     *
     * @param dType
     * @return
     */
    SysDictionary getByType(String dType);
}