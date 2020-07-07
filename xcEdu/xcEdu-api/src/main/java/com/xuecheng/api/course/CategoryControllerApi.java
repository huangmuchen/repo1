package com.xuecheng.api.course;

import com.xuecheng.model.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/6/20 10:30
 * @version: V1.0
 * @Description: 课程分类相关的对外暴露的接口：在Course服务工程编写Controller类实现此接口
 */
@Api(value = "课程分类管理", description = "课程分类管理接口，提供课程分类的查询、修改、添加", tags = "CategoryApi")
public interface CategoryControllerApi {

    /**
     * 查询课程分类列表
     *
     * @return
     */
    @ApiOperation("查询课程分类列表")
    CategoryNode findCategoryList();
}