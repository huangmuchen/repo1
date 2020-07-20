package com.xuecheng.api.learning;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.learning.response.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/7/9 21:18
 * @version: V1.0
 * @Description: Learning相关的对外暴露的接口：在Learning服务工程编写Controller类实现此接口
 */
@Api(value = "录播课程学习管理", description = "课录播课程学习管理接口，提供课程学习视频地址查询", tags = "CourseLearningApi")
public interface LearningControllerApi {

    /**
     * 查询录播课程学习地址
     *
     * @param courseId
     * @param teachplanId
     * @return
     */
    @ApiOperation("查询录播课程学习地址")
    GetMediaResult getMedia(String courseId, String teachplanId);

    /**
     * 查询用户已选课程
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @ApiOperation("查询用户已选课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "courseListRequest", value = "课程列表查询条件", paramType = "body", required = false, dataTypeClass = CourseListRequest.class)
    })
    QueryResponseResult chooseCourseList(int page, int size, CourseListRequest courseListRequest);
}