package com.xuecheng.model.domain.learning.response;

import com.xuecheng.common.model.response.ResultCode;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 学习响应状态码
 */
@ToString
public enum LearningCode implements ResultCode {
    LEARNING_GETMEDIA_FAIL(false, 40601, "获取学习视频地址失败！"),
    LEARNING_GETMEDIA_ERROR(false, 40602, "获取学习视频ID失败！"),
    CHOOSECOURSE_USERISNULL(false, 40603, "用户信息为空！"),
    CHOOSECOURSE_TASKISNULL(false, 40604, "任务信息为空！");

    // 操作是否成功
    boolean success;
    // 操作代码
    int code;
    // 提示信息
    String message;

    LearningCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

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