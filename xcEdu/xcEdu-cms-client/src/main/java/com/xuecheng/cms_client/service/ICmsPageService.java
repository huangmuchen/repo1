package com.xuecheng.cms_client.service;

import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.CmsSite;

/**
 * @author: HuangMuChen
 * @date: 2020/6/15 21:49
 * @version: V1.0
 * @Description: 页面发布业务层接口
 */
public interface ICmsPageService {

    /**
     * 根据pageId，从GridFS中获取html保存到服务器指定路径上
     *
     * @param pageId 页面id
     * @param type   发布类型
     */
    void savePageToServerPath(String pageId, String type);

    /**
     * 根据id获取CmsSite
     *
     * @param siteId 站点id
     * @return 站点信息
     */
    CmsSite getCmsSiteById(String siteId);

    /**
     * 根据id获取CmsPage
     *
     * @param pageId 页面id
     * @return 页面信息
     */
    CmsPage getCmsPageById(String pageId);
}