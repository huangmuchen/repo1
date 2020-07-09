package com.xuecheng.learning.service.impl;

import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.learning.client.CourseSearchClient;
import com.xuecheng.learning.service.ILearningService;
import com.xuecheng.model.domain.course.TeachplanMediaPub;
import com.xuecheng.model.domain.learning.response.GetMediaResult;
import com.xuecheng.model.domain.learning.response.LearningCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: HuangMuChen
 * @date: 2020/7/9 21:28
 * @version: V1.0
 * @Description: 课程学习业务层实现类
 */
@Service
public class LearningServiceImpl implements ILearningService {
    @Autowired
    private CourseSearchClient courseSearchClient;

    /**
     * 查询录播课程学习地址
     *
     * @param courseId
     * @param teachplanId
     * @return
     */
    @Override
    public GetMediaResult getMedia(String courseId, String teachplanId) {
        // TODO：校验学生的学习权限...是否资费等

        // 通过Feign远程调用Search服务
        TeachplanMediaPub teachplanMediaPub = this.courseSearchClient.getMedia(teachplanId);
        // 校验
        if (teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())) {
            // 获取视频播放地址出错
            throw new CustomException(LearningCode.LEARNING_GETMEDIA_FAIL);
        }
        // 返回查询结果
        return new GetMediaResult(CommonCode.SUCCESS, teachplanMediaPub.getMediaUrl());
    }
}