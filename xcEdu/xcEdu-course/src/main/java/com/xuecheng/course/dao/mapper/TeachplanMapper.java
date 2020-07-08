package com.xuecheng.course.dao.mapper;

import com.xuecheng.model.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/7/8 11:20
 * @version: V1.0
 * @Description: 课程计划持久层接口
 */
public interface TeachplanMapper {

    /**
     * 查询课程计划列表
     *
     * @param courseId
     * @return
     */
    @Results(id = "teachplanMap01", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "children", many = @Many(select = "getPrimaryNodeByParentId")),
    })
    @Select({"<script>" + "select * from teachplan where parentid = '0'" + "<if test='courseId!=null and courseId!=\" \"'>and courseid = #{courseId}</if>" + "order by orderby" + "</script>"})
    TeachplanNode findTeachplanList(@Param("courseId") String courseId);

    /**
     * 根据父节点id获取一级子节点集合
     *
     * @param id
     * @return
     */
    @Results(id = "teachplanMap02", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "children", many = @Many(select = "getSecondaryNodeByParentId")),
    })
    @Select("select * from teachplan where parentid = #{id} order by orderby")
    List<TeachplanNode> getPrimaryNodeByParentId(String id);

    /**
     * 根据父节点id获取二级子节点集合
     *
     * @param id
     * @return
     */
    @Select("select t.*,m.media_id mediaId,m.media_fileoriginalname mediaFileOriginalName from teachplan t left join teachplan_media m on t.id = m.teachplan_id where parentid = #{id} order by orderby")
    List<TeachplanNode> getSecondaryNodeByParentId(String id);
}