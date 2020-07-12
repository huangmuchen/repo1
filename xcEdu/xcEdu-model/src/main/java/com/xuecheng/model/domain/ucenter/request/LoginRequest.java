package com.xuecheng.model.domain.ucenter.request;

import com.xuecheng.common.model.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 封装用户登录条件
 */
@Data
@ToString
@ApiModel(value = "LoginRequest", description = "封装登录条件")
public class LoginRequest extends RequestData {
    @ApiModelProperty("用户名")
    String username;
    @ApiModelProperty("密码")
    String password;
    @ApiModelProperty("验证码")
    String verifycode;
}