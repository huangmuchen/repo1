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
 * @Description: 课程基本信息实体类
 */
@Data
@ToString
@Entity
@Table(name = "course_base")
// @GenericGenerator(name = "jpa-assigned", strategy = "assigned")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class CourseBase implements Serializable {
    private static final long serialVersionUID = -916357110051689486L;

    @Id // 主键
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id; // 课程id
    private String name; // 课程名称
    private String users; // 适用人群
    private String mt; // 课程大分类
    private String st; // 课程小分类
    private String grade; // 课程等级
    private String studymodel; // 学习模式
    private String teachmode; // 授课模式
    private String description; // 课程介绍
    private String status; // 课程状态
    @Column(name = "company_id")
    private String companyId; // 教育机构
    @Column(name = "user_id")
    private String userId; // 创建用户
}