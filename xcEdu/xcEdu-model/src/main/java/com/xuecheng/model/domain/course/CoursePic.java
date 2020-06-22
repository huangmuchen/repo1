package com.xuecheng.model.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 课程图片信息实体类
 */
@Data
@ToString
@Entity
@Table(name = "course_pic")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CoursePic implements Serializable {
    private static final long serialVersionUID = 7605194834518961478L;

    @Id // 主键
    @GeneratedValue(generator = "jpa-assigned")
    private String courseid; // 课程id
    private String pic; // 图片id
}