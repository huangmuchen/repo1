package com.xuecheng.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.course.CoursePub;
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
}