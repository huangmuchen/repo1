package com.xuecheng.model.domain.media.response;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.common.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@ToString
@AllArgsConstructor
public enum MediaCode implements ResultCode {
    // 枚举类型的实例对象建议全大写,且必须在最前面先定义，且必须按顺序维护枚举自定义的成员变量
    UPLOAD_FILE_REGISTER_FAIL(false, 22001, "上传文件在系统注册失败，请刷新页面重试！"),
    UPLOAD_FILE_REGISTER_EXIST(false, 22002, "上传文件在系统已存在！"),
    CHUNK_FILE_EXIST_CHECK(true, 22003, "分块文件在系统已存在！"),
    MERGE_FILE_FAIL(false, 22004, "合并文件失败，文件在系统已存在！"),
    MERGE_FILE_CHECKFAIL(false, 22005, "合并文件校验失败！");

    // 操作是否成功
    @ApiModelProperty(value = "媒资系统操作是否成功", example = "true", required = true)
    boolean success;
    // 操作代码
    @ApiModelProperty(value = "媒资系统操作代码", example = "22001", required = true)
    int code;
    // 提示信息
    @ApiModelProperty(value = "媒资系统操作提示", example = "文件在系统已存在！", required = true)
    String message;

    private static final ImmutableMap<Integer, MediaCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, MediaCode> builder = ImmutableMap.builder();
        for (MediaCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
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