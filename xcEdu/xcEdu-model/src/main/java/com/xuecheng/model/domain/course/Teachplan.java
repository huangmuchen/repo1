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
 * @Description: 课程计划实体类
 */
@Data
@ToString
@Entity
@Table(name = "teachplan")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Teachplan implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;

    @Id // 主键
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id; // 课程计划id
    private String pname; // 课程计划名称
    private String parentid; // 父节点id
    private String grade;// 层级，分为1、2、3级
    private String ptype;// 课程类型
    private String description;// 章节及课程时介绍
    private String courseid;// 课程id
    private String status;// 状态：未发布、已发布
    private Integer orderby;// 排序字段
    private Double timelength;// 时长，单位分钟
    private String trylearn;// 是否试学
}