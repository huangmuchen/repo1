package com.xuecheng.model.domain.portalview.response;

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
public enum PortalViewCode implements ResultCode {
    // 枚举类型的实例对象建议全大写,且必须在最前面先定义，且必须按顺序维护枚举自定义的成员变量
    PortalView_ADDVIEWCOURSE_COURSEIDISNULL(false, 42001, "课程id为空！"),
    PortalView_PUBLISH_PREVIEWCOURSE_ISNULL(false, 42002, "预览课程视图为空！"),
    PortalView_PUBLISH_PREVIEWMEDIA_ISNULL(false, 42003, "预览课程媒资视图为空！");

    // 操作是否成功
    @ApiModelProperty(value = "媒资系统操作是否成功", example = "true", required = true)
    boolean success;
    // 操作代码
    @ApiModelProperty(value = "媒资系统操作代码", example = "22001", required = true)
    int code;
    // 提示信息
    @ApiModelProperty(value = "媒资系统操作提示", example = "文件在系统已存在！", required = true)
    String message;

    private static final ImmutableMap<Integer, PortalViewCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, PortalViewCode> builder = ImmutableMap.builder();
        for (PortalViewCode commonCode : values()) {
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