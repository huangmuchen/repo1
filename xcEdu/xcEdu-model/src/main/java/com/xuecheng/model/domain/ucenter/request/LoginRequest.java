package com.xuecheng.model.domain.ucenter.request;

import com.xuecheng.common.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class LoginRequest extends RequestData {
    String username;
    String password;
    String verifycode;
}