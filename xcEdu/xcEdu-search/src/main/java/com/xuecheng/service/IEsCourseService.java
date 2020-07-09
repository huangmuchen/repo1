package com.xuecheng.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.course.CoursePub;
import com.xuecheng.model.domain.course.TeachplanMediaPub;
import com.xuecheng.model.domain.search.CourseSearchParam;

/**
 * @author: HuangMuChen
 * @date: 2020/6/30 22:26
 * @version: V1.0
 * @Description: 课程搜索业务层接口
 */
public interface IEsCourseService {

    /**
     * 分页搜索课程
     *
     * @param page
     * @param size
     * @param courseSearchParam
     * @return
     */
    QueryResponseResult<CoursePub> esList(int page, int size, CourseSearchParam courseSearchParam);

    /**
     * 根据课程ID，查询课程信息
     *
     * @param courseId
     * @return
     */
    CoursePub getDetail(String courseId);

    /**
     * 根据课程计划ID，查询播放地址
     *
     * @param teachplanId
     * @return
     */
    TeachplanMediaPub getMedia(String teachplanId);
}