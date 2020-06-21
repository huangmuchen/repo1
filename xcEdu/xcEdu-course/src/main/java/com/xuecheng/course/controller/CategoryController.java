package com.xuecheng.course.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.course.service.ICategoryService;
import com.xuecheng.model.domain.course.ext.CategoryNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/6/20 10:39
 * @version: V1.0
 * @Description: 课程分类控制层
 */
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {
    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询课程分类列表
     *
     * @return
     */
    @GetMapping("/list")
    @Override
    public CategoryNode findCategoryList() {
        return this.categoryService.findCategoryList();
    }
}