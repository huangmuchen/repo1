package com.xuecheng.ucenter.controller;

import com.xuecheng.api.ucenter.UcenterControllerApi;
import com.xuecheng.model.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.service.IUcenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/7/11 20:52
 * @version: V1.0
 * @Description: 用户中心控制层
 */
@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
    @Autowired
    private IUcenterService ucenterService;

    /**
     * 根据用户名称查询用户及扩展信息
     *
     * @param username
     * @return
     */
    @GetMapping("/getUserExt")
    public XcUserExt getUserExt(@RequestParam("username") String username) {
        // 调用service层进行查询
        return this.ucenterService.getUserExt(username);
    }
}