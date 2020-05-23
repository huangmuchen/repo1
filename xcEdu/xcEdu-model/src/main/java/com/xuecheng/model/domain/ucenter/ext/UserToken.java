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
public class UserToken {
    String userId; // 用户id
    String utype; // 用户类型
    String companyId; // 用户所属企业信息
}