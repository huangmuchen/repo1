package com.xuecheng.api.course;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.course.Teachplan;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/6/18 13:19
 * @version: V1.0
 * @Description: Course(课程)管理相关的对外暴露的接口：在Course服务工程编写Controller类实现此接口
 */
@Api(value = "course管理接口", description = "course管理接口，提供课程的查询、新增、修改、营销、计划", tags = "CourseApi")
public interface CourseControllerApi {

    /**
     * 分页查询课程列表
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @ApiOperation("分页查询课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    /**
     * 根据课程id查询课程计划列表
     *
     * @param courseId
     * @return
     */
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String")
    @ApiOperation("查询课程计划列表")
    TeachplanNode findTeachplanList(String courseId);

    /**
     * 添加课程计划
     *
     * @param teachplan
     * @return
     */
    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);
}