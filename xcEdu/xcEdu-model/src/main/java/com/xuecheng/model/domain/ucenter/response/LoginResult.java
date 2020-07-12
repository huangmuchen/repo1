package com.xuecheng.model.domain.ucenter.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 登录认证响应模型
 */
@Data
@ToString
@NoArgsConstructor
public class LoginResult extends ResponseResult {
    private String token; // 令牌

    public LoginResult(ResultCode resultCode, String token) {
        super(resultCode);
        this.token = token;
    }
}