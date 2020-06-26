package com.xuecheng.model.domain.course.ext;

import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.CourseMarket;
import com.xuecheng.model.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 课程详情页数据响应结果类型
 */
@Data
@ToString
@NoArgsConstructor
public class CourseView implements Serializable {
    private static final long serialVersionUID = -7090826563594642392L;

    CourseBase courseBase; // 课程基础信息
    CourseMarket courseMarket; // 课程营销信息
    CoursePic coursePic; // 课程图片信息
    TeachplanNode teachplanNode; // 教学计划信息
}