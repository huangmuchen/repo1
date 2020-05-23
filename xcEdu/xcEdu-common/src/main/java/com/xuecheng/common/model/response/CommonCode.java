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
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}