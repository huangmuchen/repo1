package com.xuecheng.model.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 令牌封装对象
 */
@Data
@ToString
@NoArgsConstructor
public class AuthToken {
    String jti_token; // 访问令牌，与jwt令牌对应的唯一标识
    String refresh_token; // 刷新令牌，使用此令牌可以延长访问令牌的过期时间
    String jwt_token; // jwt令牌
}