package com.xuecheng.cms.service.impl;

import com.xuecheng.cms.dao.CmsConfigRepository;
import com.xuecheng.cms.service.ICmsConfigService;
import com.xuecheng.model.domain.cms.CmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: HuangMuChen
 * @date: 2020/6/10 21:45
 * @version: V1.0
 * @Description: cms数据模型业务层实现类
 */
@Service
public class CmsConfigServiceImpl implements ICmsConfigService {
    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    /**
     * 根据id查询CMS配置信息
     *
     * @param id 主键
     * @return 数据模型
     */
    @Override
    public CmsConfig getModel(String id) {
        // 调用dao进行查询
        Optional<CmsConfig> optional = this.cmsConfigRepository.findById(id);
        // 返回查询结果
        return optional.orElse(null);
    }
}