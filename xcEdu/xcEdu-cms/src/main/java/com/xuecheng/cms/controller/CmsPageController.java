package com.xuecheng.cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return                 页面列表
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        // 调用Service层进行查询,并返回结果
        return this.cmsPageService.findList(page, size, queryPageRequest);
    }
}