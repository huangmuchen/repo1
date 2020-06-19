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
 * @Description: TODO
 */
@Data
@ToString
@Entity
@Table(name = "teachplan_media")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class TeachplanMedia implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;

    @Id // 声明主键
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name = "teachplan_id")
    private String teachplanId; // 课程计划id
    @Column(name = "media_id")
    private String mediaId; // 媒资文件id
    @Column(name = "media_fileoriginalname")
    private String mediaFileOriginalName; // 媒资文件的原始名称
    @Column(name = "media_url")
    private String mediaUrl; // 媒资文件访问地址
    private String courseId; // 课程id
}