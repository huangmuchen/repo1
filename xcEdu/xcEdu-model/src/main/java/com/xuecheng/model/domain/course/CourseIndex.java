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
 * @Description: TODO
 */
@Data
@ToString
@Entity
@Table(name = "course_index")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CourseIndex implements Serializable {
    private static final long serialVersionUID = 2093920044978840752L;

    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;
    private String name;
    private String users;
    private String mt;
    private String st;
    private String grade;
    private String studymodel;
    private String teachmode;
    private String description;
    private String pic; // 图片
    private Date timestamp; // 时间戳
    private String charge;
    private String valid;
    private String qq;
    private Float price;
    private Float price_old;
    private Date expires;
    private String teachplan; // 课程计划
}