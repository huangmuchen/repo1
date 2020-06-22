package com.xuecheng.cms.service.impl;

import com.xuecheng.cms.dao.SysDictionaryRepository;
import com.xuecheng.cms.service.ISysDictionaryService;
import com.xuecheng.model.domain.system.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: HuangMuChen
 * @date: 2020/6/22 13:27
 * @version: V1.0
 * @Description: 数据字典业务层实现类
 */
@Service
public class SysDictionaryServiceImpl implements ISysDictionaryService {
    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    /**
     * 根据dType获取数据字典
     *
     * @param dType
     * @return
     */
    @Override
    public SysDictionary getByType(String dType) {
        // 调用dao层进行查询
        return this.sysDictionaryRepository.findByDType(dType);
    }
}