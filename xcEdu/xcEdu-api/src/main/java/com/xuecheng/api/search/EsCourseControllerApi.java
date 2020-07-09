package com.xuecheng.api.search;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.course.CoursePub;
import com.xuecheng.model.domain.course.TeachplanMediaPub;
import com.xuecheng.model.domain.search.CourseSearchParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/6/30 22:21
 * @version: V1.0
 * @Description: 课程搜索相关的对外暴露的接口：在Search服务工程编写Controller类实现此接口
 */
@Api(value = "课程搜索", description = "课程搜索接口，从ElasticSearch中搜索课程信息", tags = "EsCourseApi")
public interface EsCourseControllerApi {

    /**
     * 分页搜索课程
     *
     * @param page
     * @param size
     * @param courseSearchParam
     * @return
     */
    @ApiOperation("分页搜索课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "courseSearchParam", value = "课程搜索条件", paramType = "body", required = false, dataTypeClass = CourseSearchParam.class)
    })
    QueryResponseResult<CoursePub> esList(int page, int size, CourseSearchParam courseSearchParam);

    /**
     * 根据课程ID，查询课程信息
     *
     * @param courseId
     * @return
     */
    @ApiOperation("根据课程ID，查询课程信息")
    @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, paramType = "path", dataType = "String")
    CoursePub getDetail(String courseId);

    /**
     * 根据课程计划ID，查询播放地址
     *
     * @param teachplanId
     * @return
     */
    @ApiOperation("根据课程计划ID，查询播放地址")
    @ApiImplicitParam(name = "teachplanId", value = "课程计划ID", required = true, paramType = "path", dataType = "String")
    TeachplanMediaPub getMedia(String teachplanId);
}