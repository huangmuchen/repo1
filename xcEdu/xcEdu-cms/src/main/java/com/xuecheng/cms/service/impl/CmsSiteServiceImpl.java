package com.xuecheng.cms.service.impl;

import com.xuecheng.cms.dao.CmsSiteRepository;
import com.xuecheng.cms.service.ICmsSiteService;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.model.domain.cms.CmsSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 20:39
 * @version: V1.0
 * @Description: 站点模型业务层实现类
 */
@Service
public class CmsSiteServiceImpl implements ICmsSiteService {
    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    /**
     * 查询站点模型
     *
     * @return
     */
    @Override
    public QueryResponseResult findCmsSiteList() {
        try {
            // 调用dao层进行查询
            List<CmsSite> cmsSiteList = this.cmsSiteRepository.findAll();
            // 构建返回结果对象
            QueryResult<CmsSite> queryResult = new QueryResult<>();
            // 封装查询结果
            queryResult.setList(cmsSiteList);
            queryResult.setTotal(cmsSiteList.size());
            // 返回查询成功结果
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回查询失败结果
            return new QueryResponseResult(CommonCode.FAIL, new QueryResult<CmsSite>());
        }
    }
}
