package com.xuecheng.cms.service.impl;

import com.xuecheng.cms.dao.CmsTemplateRepository;
import com.xuecheng.cms.service.ICmsTemplateService;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.model.domain.cms.CmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 23:58
 * @version: V1.0
 * @Description: 页面模板业务层实现类
 */
@Service
public class CmsTemplateServiceImpl implements ICmsTemplateService {
    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    /**
     * 查询页面模板列表
     *
     * @return
     */
    @Override
    public QueryResponseResult findCmsTemplateList() {
        try {
            // 调用dao层进行查询
            List<CmsTemplate> cmsTemplateList = this.cmsTemplateRepository.findAll();
            // 构建返回结果对象
            QueryResult<CmsTemplate> queryResult = new QueryResult<>();
            // 封装查询结果
            queryResult.setList(cmsTemplateList);
            queryResult.setTotal(cmsTemplateList.size());
            // 返回查询成功结果
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回查询失败结果
            return new QueryResponseResult(CommonCode.FAIL, new QueryResult<CmsTemplate>());
        }
    }
}