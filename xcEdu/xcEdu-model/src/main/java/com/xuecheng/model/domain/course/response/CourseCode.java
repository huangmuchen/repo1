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
 * @Description: 课程响应状态码
 */
@ToString
@AllArgsConstructor
public enum CourseCode implements ResultCode {
    COURSE_PLAN_UPDATE_ERROR(false,31012,"课程计划信息更新失败！"),
    COURSE_PLAN_NOTEXIST(false,31011,"课程计划信息为空！"),
    COURSE_PUBLISH_CREATE_INDEX_ERROR(false,31010,"创建课程索引信息失败"),
    COURSE_MARKET_NOTEXIST(false,31008,"课程营销信息为空！"),
    COURSE_MARKET_UPDATE_ERROR(false,31009,"课程营销信息更新失败！"),
    COURSE_BASE_NOTEXIST(false,31006,"课程基础信息为空！"),
    COURSE_BASE_UPDATE_ERROR(false,31007,"课程基础信息更新失败！"),
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