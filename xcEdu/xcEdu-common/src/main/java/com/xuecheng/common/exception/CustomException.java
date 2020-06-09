package com.xuecheng.common.exception;

import com.xuecheng.common.model.response.ResultCode;

/**
 * @author: HuangMuChen
 * @date: 2020/5/28 13:52
 * @version: V1.0
 * @Description: 自定义异常类
 */
public class CustomException extends RuntimeException {
    // 错误代码
    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        // 异常信息为：错误状态码 + 异常信息
        super("错误状态码：" + resultCode.code() + "，异常信息：" + resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }
}