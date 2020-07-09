package com.xuecheng.course.client;

import com.xuecheng.common.client.XcServiceList;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.response.CmsPageResult;
import com.xuecheng.model.domain.cms.response.CmsPublishPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: HuangMuChen
 * @date: 2020/6/25 18:13
 * @version: V1.0
 * @Description: 通过Feign远程调用CmsPage服务
 */
@FeignClient(value = XcServiceList.XCEDU_CMS)
public interface CmsPageClient {

    /**
     * 根据页面id查询页面
     *
     * @param pageId
     * @return
     */
    @GetMapping("/cms/page/find/{pageId}")
    CmsPage findByPageId(@PathVariable("pageId") String pageId);

    /**
     * 添加课程详情页
     *
     * @param cmsPage
     * @return
     */
    @PostMapping("/cms/page/save")
    CmsPageResult saveCoursePage(@RequestBody CmsPage cmsPage);

    /**
     * 发布课程详情页
     *
     * @param cmsPage
     * @return
     */
    @PostMapping("/cms/page/publish")
    CmsPublishPageResult publishPageQuick(@RequestBody CmsPage cmsPage);
}