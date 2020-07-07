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
 * @Description: 课程计划与媒资关联实体类
 */
@Data
@ToString
@Entity
@Table(name="teachplan_media_pub")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class TeachplanMediaPub implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;

    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name="teachplan_id")
    private String teachplanId;
    @Column(name="media_id")
    private String mediaId;
    @Column(name="media_fileoriginalname")
    private String mediaFileOriginalName;
    @Column(name="media_url")
    private String mediaUrl;
    @Column(name="courseid")
    private String courseId;
    private Date timestamp;
}