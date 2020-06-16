package com.xuecheng.cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import com.xuecheng.model.domain.cms.response.CmsPageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: HuangMuChen
 * @date: 2020/5/26 15:49
 * @version: V1.0
 * @Description: 页面信息控制层
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    private ICmsPageService cmsPageService;

    /**
     * 分页查询页面信息
     *
     * @param page             页码
     * @param size             每页显示的条数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findCmsPageList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        // 调用Service层进行查询,并返回结果
        return this.cmsPageService.findCmsPageList(page, size, queryPageRequest);
    }

    /**
     * 页面添加
     *
     * @param cmsPage 新增页面
     * @return 添加结果
     */
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) { // 前端返回json格式的字符串（Vue使用axios请求数据，默认post请求传参是json字符串格式）
        return this.cmsPageService.add(cmsPage);
    }

    /**
     * 根据页面id查询页面
     *
     * @param pageId 页面id
     * @return 查询结果
     */
    @Override
    @GetMapping("/find/{pageId}")
    public CmsPage findByPageId(@PathVariable("pageId") String pageId) {
        return this.cmsPageService.findByPageId(pageId);
    }

    /**
     * 页面修改
     *
     * @param pageId  页面id
     * @param cmsPage 要修改的值
     * @return 修改结果
     */
    @Override
    @PutMapping("/edit/{pageId}")
    public CmsPageResult edit(@PathVariable("pageId") String pageId, @RequestBody CmsPage cmsPage) {
        return this.cmsPageService.update(pageId, cmsPage);
    }

    /**
     * 根据pageId删除页面
     *
     * @param pageId 页面id
     * @return 删除结果
     */
    @Override
    @DeleteMapping("/del/{pageId}")
    public ResponseResult del(@PathVariable("pageId") String pageId) {
        return this.cmsPageService.del(pageId);
    }

    /**
     * 根据pageId发布页面
     *
     * @param pageId 页面id
     * @return 发布结果
     */
    @PostMapping("/release/{pageId}")
    public ResponseResult release(@PathVariable("pageId") String pageId) {
        return this.cmsPageService.release(pageId);
    }
}