package com.xuecheng.model.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 课程发布实体类：课程信息分成了多张表，同步起来很复杂，所以使用course_pub表将所有数据整合起来，以供ES索引库维护
 */
@Data
@ToString
@Entity
@Table(name = "course_pub")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CoursePub implements Serializable {
    private static final long serialVersionUID = 5392709964739508348L;

    @Id // 主键，也是课程id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;

    // 课程基础信息（CourseBase）
    private String name;
    private String users;
    private String mt;
    private String grade;
    private String studymodel;
    private String teachmode;
    private String description;
    private String st;
    // 课程图片信息（CoursePic）
    private String pic;
    // 课程营销信息（CourseMarket）
    private String charge;
    private String valid;
    private String expires;
    private String qq;
    private Float price;
    private Float price_old;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
    // 课程计划信息（Teachplan），转成JSON字符串
    private String teachplan;
    // 课程发布时间
    @Column(name = "pub_time")
    private String pubTime;
    // 时间戳,logstash使用
    private Date timestamp;
}