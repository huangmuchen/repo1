package com.xuecheng.model.domain.learning;

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
 * @Description: 选课实体类
 */
@Data
@ToString
@Entity
@Table(name = "xc_learning_course")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcLearningCourse implements Serializable {
    private static final long serialVersionUID = -916357210051789799L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id; // 主键
    @Column(name = "course_id")
    private String courseId; // 课程id
    @Column(name = "user_id")
    private String userId; // 用户id
    private String valid; // 有效性
    @Column(name = "start_time")
    private Date startTime; // 开始时间
    @Column(name = "end_time")
    private Date endTime; // 结束时间
    private String status; // 选课状态
}