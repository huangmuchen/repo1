package com.xuecheng.course.service;

import com.xuecheng.model.domain.course.ext.CategoryNode;

/**
 * @author: HuangMuChen
 * @date: 2020/6/20 10:50
 * @version: V1.0
 * @Description: 课程分类业务层接口
 */
public interface ICategoryService {

    /**
     * 查询课程分类列表
     *
     * @return
     */
    CategoryNode findCategoryList();
}