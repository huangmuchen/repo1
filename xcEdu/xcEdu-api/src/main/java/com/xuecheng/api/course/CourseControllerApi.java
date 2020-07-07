package com.xuecheng.api.course;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.CourseMarket;
import com.xuecheng.model.domain.course.CoursePic;
import com.xuecheng.model.domain.course.Teachplan;
import com.xuecheng.model.domain.course.ext.CourseView;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.course.response.AddCourseResult;
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
@Api(value = "课程管理", description = "课程管理接口，提供课程的查询、新增、修改、营销、计划", tags = "CourseApi")
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
            @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "courseListRequest", value = "课程列表查询条件", paramType = "body", required = false, dataTypeClass = CourseListRequest.class)
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
     * 根据课程计划id查询课程计划
     *
     * @param teachplanId
     * @return
     */
    @ApiImplicitParam(name = "teachplanId", value = "课程计划id", required = true, paramType = "path", dataType = "String")
    @ApiOperation("根据课程计划id查询课程计划")
    Teachplan findTeachplan(String teachplanId);

    /**
     * 添加课程计划
     *
     * @param teachplan
     * @return
     */
    @ApiOperation("添加课程计划")
    @ApiImplicitParam(name = "teachplan", value = "课程计划信息", paramType = "body", required = true, dataTypeClass = Teachplan.class)
    ResponseResult addTeachplan(Teachplan teachplan);

    /**
     * 修改课程计划
     *
     * @param teachplan
     * @return
     */
    @ApiOperation("修改课程计划")
    @ApiImplicitParam(name = "teachplan", value = "课程计划信息", paramType = "body", required = true, dataTypeClass = Teachplan.class)
    ResponseResult updateTeachplan(Teachplan teachplan);

    /**
     * 删除课程计划
     *
     * @param teachplanNode
     * @return
     */
    @ApiOperation("删除课程计划")
    @ApiImplicitParam(name = "teachplanNode", value = "课程计划树信息", paramType = "body", required = true, dataTypeClass = TeachplanNode.class)
    ResponseResult deleteTeachPlan(TeachplanNode teachplanNode);

    /**
     * 添加课程基础信息
     *
     * @param courseBase
     * @return
     */
    @ApiOperation("添加课程基础信息")
    @ApiImplicitParam(name = "courseBase", value = "课程信息", paramType = "body", required = true, dataTypeClass = CourseBase.class)
    AddCourseResult addCourseBase(CourseBase courseBase);

    /**
     * 查询课程基础信息
     *
     * @param courseId
     * @return
     */
    @ApiOperation("查询课程基础信息")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String")
    CourseBase getCourseBaseById(String courseId);

    /**
     * 更新课程基础信息
     *
     * @param courseId
     * @param courseBase
     * @return
     */
    @ApiOperation("更新课程基础信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "courseBase", value = "课程信息", paramType = "body", required = true, dataTypeClass = CourseBase.class)
    })
    ResponseResult updateCourseBase(String courseId, CourseBase courseBase);

    /**
     * 查询课程营销信息
     *
     * @param id
     * @return
     */
    @ApiOperation("查询课程营销信息")
    @ApiImplicitParam(name = "id", value = "课程营销id", required = true, paramType = "path", dataType = "String")
    CourseMarket getCourseMarketById(String id);

    /**
     * 更新课程营销信息
     *
     * @param id
     * @param courseMarket
     * @return
     */
    @ApiOperation("更新课程营销信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程营销id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "courseMarket", value = "课程营销信息", paramType = "body", required = true, dataTypeClass = CourseMarket.class)
    })
    ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);

    /**
     * 添加课程图片
     *
     * @param courseId
     * @param pic
     * @return
     */
    @ApiOperation("添加课程图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "pic", value = "图片id", required = true, paramType = "path", dataType = "String")
    })
    ResponseResult addCoursePic(String courseId, String pic);

    /**
     * 查询课程图片
     *
     * @param courseId
     * @return
     */
    @ApiOperation("查询课程图片")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String")
    CoursePic findCoursePic(String courseId);

    /**
     * 删除课程图片
     *
     * @param courseId
     * @return
     */
    @ApiOperation("删除课程图片")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String")
    ResponseResult deleteCoursePic(String courseId);

    /**
     * 课程数据模型查询
     *
     * @param courseId
     * @return
     */
    @ApiOperation("课程视图查询")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, paramType = "path", dataType = "String")
    CourseView getCourseView(String courseId);
}