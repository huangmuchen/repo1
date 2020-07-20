package com.xuecheng.learning.dao.mapper;

import com.github.pagehelper.Page;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.learning.XcLearningCourse;
import org.apache.ibatis.annotations.Select;

/**
 * @author: HuangMuChen
 * @date: 2020/7/20 14:26
 * @version: V1.0
 * @Description: 课程学习持久层接口
 */
public interface LearningCourseMapper {

    /**
     * 根据用户id查询用户已选课程
     *
     * @param courseListRequest
     * @return
     */
    @Select("select id,course_id courseId,user_id userId,valid,start_time startTime,end_time endTime,status from xc_learning_course where user_id =#{userId}")
    Page<XcLearningCourse> chooseCourseList(CourseListRequest courseListRequest);
}