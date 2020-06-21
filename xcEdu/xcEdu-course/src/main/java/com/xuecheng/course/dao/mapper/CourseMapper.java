package com.xuecheng.course.dao.mapper;

import com.github.pagehelper.Page;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.ext.CourseInfo;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/6/18 21:22
 * @version: V1.0
 * @Description: 课程管理持久层接口
 */
public interface CourseMapper {

    /**
     * 根据id查询课程基本信息
     *
     * @param id
     * @return
     */
    @Select("select * from course_base where id = #{id}")
    CourseBase findCourseBaseById(String id);

    /**
     * 分页查询课程列表：
     * select course_base.*,(select pic from course_pic where courseid = course_base.id) pic from course_base
     *
     * @param courseListRequest
     * @return
     */
    @Select({"<script>" + "select b.*,p.pic from course_base b left join course_pic p on b.id = p.courseid where 1=1" + "<if test='companyId!=null and companyId!=\" \"'>and b.company_id = #{companyId}</if>" + "</script>"})
    Page<CourseInfo> findCourseList(CourseListRequest courseListRequest);

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
    @Select("select * from teachplan where parentid = #{id} order by orderby")
    List<TeachplanNode> getSecondaryNodeByParentId(String id);
}