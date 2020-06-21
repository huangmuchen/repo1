package com.xuecheng.course.dao.mapper;

import com.xuecheng.model.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/6/20 10:42
 * @version: V1.0
 * @Description: 课程分类持久层接口
 */
public interface CategoryMapper {

    /**
     * 查询课程分类列表
     *
     * @return
     */
    @Results(id = "categoryMap01", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "children", many = @Many(select = "getPrimaryNodeByParentId")),
    })
    @Select("select * from category where parentid='0' order by orderby")
    CategoryNode findCategoryList();

    /**
     * 根据父节点id获取一级子节点集合
     *
     * @param id
     * @return
     */
    @Results(id = "categoryMap02", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "children", many = @Many(select = "getSecondaryNodeByParentId")),
    })
    @Select("select * from category where parentid = #{id} order by orderby")
    List<CategoryNode> getPrimaryNodeByParentId(String id);

    /**
     * 根据父节点id获取二级子节点集合
     *
     * @param id
     * @return
     */
    @Select("select * from category where parentid = #{id} order by orderby")
    List<CategoryNode> getSecondaryNodeByParentId(String id);
}