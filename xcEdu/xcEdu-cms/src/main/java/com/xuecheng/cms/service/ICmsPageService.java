package com.xuecheng.cms.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;

/**
 * @author: HuangMuChen
 * @date: 2020/5/28 13:50
 * @version: V1.0
 * @Description: 页面信息业务层接口
 */
public interface ICmsPageService {

    /**
     * 分页查询页面信息
     *
     * @param page             页码
     * @param size             每页显示的条数
     * @param queryPageRequest 查询条件
     * @return                 页面列表
     */
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
}