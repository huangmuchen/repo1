package com.xuecheng.api.course;

import com.xuecheng.model.domain.course.response.CoursePublishResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/6/26 22:44
 * @version: V1.0
 * @Description: 课程页面预览/发布相关的对外暴露的接口：在Course服务工程编写Controller类实现此接口
 */
@Api(value = "课程页面管理接口", description = "课程页面管理接口，提供课程页面的预览、发布", tags = "CoursePageApi")
public interface CoursePageControllerApi {

    /**
     * 课程预览
     *
     * @param courseId
     * @return
     */
    @ApiOperation("课程预览")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String")
    CoursePublishResult coursePreview(String courseId);

    /**
     * 课程发布
     *
     * @param courseId
     * @return
     */
    @ApiOperation("课程发布")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String")
    CoursePublishResult coursePublish(String courseId);
}