package com.xuecheng.learning.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.learning.response.GetMediaResult;
import com.xuecheng.model.domain.task.XcTask;

import java.util.Date;

/**
 * @author: HuangMuChen
 * @date: 2020/7/9 21:27
 * @version: V1.0
 * @Description: 课程学习业务层接口
 */
public interface ILearningService {

    /**
     * 查询录播课程学习地址
     *
     * @param courseId
     * @param teachplanId
     * @return
     */
    GetMediaResult getMedia(String courseId, String teachplanId);

    /**
     * 自动添加课程
     *
     * @param userId
     * @param courseId
     * @param valid
     * @param startTime
     * @param endTime
     * @param xcTask
     * @return
     */
    ResponseResult addcourse(String userId, String courseId, String valid, Date startTime, Date endTime, XcTask xcTask);

    /**
     * 发送选课添加成功消息给mq
     *
     * @param task
     */
    void sendFinishChooseCourseMsg(XcTask task);

    /**
     * 根据用户id查询用户已选课程
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @param userId
     * @return
     */
    QueryResponseResult chooseCourseList(int page, int size, String userId, CourseListRequest courseListRequest);
}