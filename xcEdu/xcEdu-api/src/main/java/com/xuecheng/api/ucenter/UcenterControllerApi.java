package com.xuecheng.api.ucenter;

import com.xuecheng.model.domain.ucenter.ext.XcUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/7/11 20:53
 * @version: V1.0
 * @Description: 用户中心对外暴露的接口，在Ucenter服务工程编写Controller类实现此接口
 */
@Api(value = "用户管理", description = "用户管理接口，负责用户的注册、登录、学习、选课、考试等功能", tags = "UcenterApi")
public interface UcenterControllerApi {

    /**
     * 根据用户名称查询用户及扩展信息
     *
     * @param username
     * @return
     */
    @ApiOperation("根据用户名称查询用户及扩展信息")
    @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "path", dataType = "String")
    XcUserExt getUserExt(String username);
}