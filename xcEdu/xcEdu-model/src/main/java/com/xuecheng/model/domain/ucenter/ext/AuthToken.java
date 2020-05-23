package com.xuecheng.model.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
@NoArgsConstructor
public class AuthToken {
    String access_token; // 访问token
    String refresh_token; // 刷新token
    String jwt_token; // jwt令牌
}