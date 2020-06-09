package com.xuecheng.cms.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import com.xuecheng.model.domain.cms.response.CmsPageResult;

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
     * @return 页面列表
     */
    QueryResponseResult findCmsPageList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 页面添加
     *
     * @param cmsPage 新增页面
     * @return 添加结果
     */
    CmsPageResult add(CmsPage cmsPage);

    /**
     * 根据页面id查询页面
     *
     * @param pageId 页面id
     * @return 查询结果
     */
    CmsPage findByPageId(String pageId);

    /**
     * 页面修改
     *
     * @param pageId  页面id
     * @param cmsPage 要修改的值
     * @return 更新结果
     */
    CmsPageResult update(String pageId, CmsPage cmsPage);

    /**
     * 根据pageId删除页面
     *
     * @param pageId 页面id
     * @return 删除结果
     */
    ResponseResult del(String pageId);
}