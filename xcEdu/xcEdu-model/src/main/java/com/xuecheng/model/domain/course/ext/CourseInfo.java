package com.xuecheng.model.domain.course.ext;

import com.xuecheng.model.domain.course.CourseBase;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class CourseInfo extends CourseBase {
    private String pic; // 课程图片
}