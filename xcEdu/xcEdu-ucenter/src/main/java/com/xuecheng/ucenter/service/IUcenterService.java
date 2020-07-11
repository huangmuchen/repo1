package com.xuecheng.ucenter.service;

import com.xuecheng.model.domain.ucenter.ext.XcUserExt;

/**
 * @author: HuangMuChen
 * @date: 2020/7/11 20:55
 * @version: V1.0
 * @Description: 用户中心业务层接口
 */
public interface IUcenterService {

    /**
     * 根据用户名称查询用户及扩展信息
     *
     * @param username
     * @return
     */
    XcUserExt getUserExt(String username);
}