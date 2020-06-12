package com.xuecheng.common.model.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 16:51
 * @version: V1.0
 * @Description: 异常枚举类
 */
@ToString
@AllArgsConstructor // 生成各种带参构造
public enum CommonCode implements ResultCode {
    REQUIRED_METHOD_ERROR(false,402,"请求方法错误！"),
    CHARSET_NAME_ERROR(false,10004,"字符编码名称错误！"),
    INVALID_PARAM(false,10003,"非法参数！"),
    SUCCESS(true, 10000, "操作成功！"),
    FAIL(false, 11111, "操作失败！"),
    UNAUTHENTICATED(false, 10001, "此操作需要登陆系统！"),
    UNAUTHORISE(false, 10002, "权限不足，无权操作！"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！");

    // 操作是否成功
    boolean success;
    // 操作代码
    int code;
    // 提示信息
    String message;

    @Override
    public boolean success() {
        return this.success;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}