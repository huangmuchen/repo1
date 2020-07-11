package com.xuecheng.auth.client;

import com.xuecheng.common.client.XcServiceList;
import com.xuecheng.model.domain.ucenter.ext.XcUserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:10
 * @version: V1.0
 * @Description: 通过Feign远程调用ucenter服务
 */
@FeignClient(XcServiceList.XCEDU_UCENTER)
public interface UserClient {

    /**
     * 查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/ucenter/getUserExt")
    XcUserExt getUserExt(@RequestParam("username") String username);
}