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
public class UserBasicInfo {
    private String id;
    private String username;
    private String userpic;
    private String name;
    private String utype;
    private String companyId; // 所属企业信息
    private String jwt_token; // jwt令牌
}