package com.xuecheng.learning.service;

import com.xuecheng.model.domain.learning.response.GetMediaResult;

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
}