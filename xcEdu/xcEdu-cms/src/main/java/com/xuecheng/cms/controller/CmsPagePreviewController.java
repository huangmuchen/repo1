package com.xuecheng.cms.controller;

import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;

/**
 * @author: HuangMuChen
 * @date: 2020/6/13 2:19
 * @version: V1.0
 * @Description: 页面预览控制层
 */
@Controller
@RequestMapping("/cms/preview")
public class CmsPagePreviewController extends BaseController {
    @Autowired
    private ICmsPageService cmsPageService;

    /**
     * 页面预览
     *
     * @param pageId
     */
    @GetMapping("/{pageId}")
    public void preview(@PathVariable("pageId") String pageId) { // 当路径上的参数名和形参相同，@PathVariable注解后面的"pageId"可以省略
        try {
            // 调用service层获取静态化页面
            String html = this.cmsPageService.preview(pageId);
            // 将静态化页面响应给客户端
            response.getOutputStream().write(html.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}