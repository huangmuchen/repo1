package com.xuecheng.model.domain.course.response;

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
public enum CourseCode implements ResultCode {
    // 枚举类型的实例对象建议全大写,且必须在最前面先定义，且必须按顺序维护枚举自定义的成员变量
    COURSE_DENIED_DELETE(false, 31001, "删除课程失败，只允许删除本机构的课程！"),
    COURSE_PUBLISH_PERVIEWISNULL(false, 31002, "还没有进行课程预览！"),
    COURSE_PUBLISH_CDETAILERROR(false, 31003, "创建课程详情页面出错！"),
    COURSE_PUBLISH_COURSEIDISNULL(false, 31004, "课程Id为空！"),
    COURSE_PUBLISH_VIEWERROR(false, 31005, "发布课程视图出错！"),
    COURSE_MEDIS_URLISNULL(false, 31101, "选择的媒资文件访问地址为空！"),
    COURSE_MEDIS_NAMEISNULL(false, 31102, "选择的媒资文件名称为空！");

    // 操作是否成功
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;
    // 操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    // 提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;

    private static final ImmutableMap<Integer, CourseCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, CourseCode> builder = ImmutableMap.builder();
        for (CourseCode commonCode : values()) {
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