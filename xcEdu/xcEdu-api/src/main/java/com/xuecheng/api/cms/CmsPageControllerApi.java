package com.xuecheng.api.cms;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 23:08
 * @version: V1.0
 * @Description: CmsPage相关的对外暴露的接口：在CMS服务工程编写Controller类实现此接口
 */
public interface CmsPageControllerApi {

    /**
     * 分页查询CmsPage
     *
     * @param page             页码
     * @param size             每页显示的条数
     * @param queryPageRequest 封装的查询对象
     * @return                 封装的响应对象
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
}