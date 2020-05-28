package com.xuecheng.cms.service.impl;

import com.xuecheng.cms.dao.CmsPageRepository;
import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author: HuangMuChen
 * @date: 2020/5/28 13:52
 * @version: V1.0
 * @Description: 页面信息业务层实现类
 */
@Service
public class CmsPageServiceImpl implements ICmsPageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    /**
     * 分页查询页面信息
     *
     * @param page             页码
     * @param size             每页显示的条数
     * @param queryPageRequest 查询条件
     * @return                 页面列表
     */
    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        try {
            // 初始化page
            if (page <= 0) {
                page = 1;
            }
            // 初始化size
            if (size <= 0) {
                size = 10;
            }
            // 初始化查询条件
            if (queryPageRequest == null) {
                queryPageRequest = new QueryPageRequest();
            }
            // 封装分页对象,默认从0开始索引页(为了适应mongodb的接口将页码减1)
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            // 构建Example对象
            CmsPage cmsPage = new CmsPage();
            // 属性复制
            BeanUtils.copyProperties(queryPageRequest, cmsPage);
            Example<CmsPage> example = Example.of(cmsPage);
            // 分页查询
            Page<CmsPage> all = this.cmsPageRepository.findAll(example, pageRequest);
            // 构建返回结果对象,并封装查询结果
            QueryResult<CmsPage> queryResult = new QueryResult<>();
            // 设置页面列表
            queryResult.setList(all.getContent());
            // 设置总条数
            queryResult.setTotal(all.getTotalElements());
            // 返回查询成功结果
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回查询失败结果
            return new QueryResponseResult(CommonCode.FAIL, new QueryResult<CmsPage>());
        }
    }
}