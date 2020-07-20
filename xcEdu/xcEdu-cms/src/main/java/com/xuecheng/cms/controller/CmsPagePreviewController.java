package com.xuecheng.cms.controller;

import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.web.BaseController;
import com.xuecheng.utils.Oauth2Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "cms页面预览接口", description = "cms页面预览接口，提供页面的预览", tags = "CmsPagePreviewApi")
public class CmsPagePreviewController extends BaseController {
    @Autowired
    private ICmsPageService cmsPageService;

    /**
     * 页面预览
     *
     * @param pageId
     */
    @ApiOperation("页面预览")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, paramType = "path", dataType = "String")
    @GetMapping("/{pageId}")
    public void preview(@PathVariable("pageId") String pageId) { // 当路径上的参数名和形参相同，@PathVariable注解后面的"pageId"可以省略
        try {
            // 从请求头中取出jwt信息
            String jwt = Oauth2Util.getJwtFromHeader(request);
            // 调用service层获取静态化页面
            String html = this.cmsPageService.preview(pageId,jwt);
            // 必须设置请求头为html, nginx才能解析ssi，否则nginx不解析
            response.setHeader("Content-type","text/html;charset=utf-8");
            // 将静态化页面响应给客户端
            response.getOutputStream().write(html.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}