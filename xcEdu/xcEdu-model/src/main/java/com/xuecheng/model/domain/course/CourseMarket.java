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
 * @Description: 课程营销信息实体类
 */
@Data
@ToString
@Entity
@Table(name = "course_market")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CourseMarket implements Serializable {
    private static final long serialVersionUID = -9120729158815039609L;

    @Id // 主键
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id; // 课程id
    private String charge; // 收费规则，对应数据字典
    private String valid; // 有效性，对应数据字典
    private String qq; // 咨询qq
    private Double price; // 价格
    private Double price_old; // 原价
    private Date expires; // 过期时间
    @Column(name = "start_time")
    private Date startTime; // 课程有效期-开始时间
    @Column(name = "end_time")
    private Date endTime; // 课程有效期-结束时间
}