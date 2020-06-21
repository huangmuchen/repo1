package com.xuecheng.course.service.impl;

import com.xuecheng.course.dao.mapper.CategoryMapper;
import com.xuecheng.course.service.ICategoryService;
import com.xuecheng.model.domain.course.ext.CategoryNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: HuangMuChen
 * @date: 2020/6/20 10:55
 * @version: V1.0
 * @Description: 课程分类业务层实现类
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询课程分类列表
     *
     * @return
     */
    @Override
    public CategoryNode findCategoryList() {
        return this.categoryMapper.findCategoryList();
    }
}