package com.xuecheng.course.dao.mapper;

import com.github.pagehelper.Page;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.ext.CourseInfo;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Select;

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
}