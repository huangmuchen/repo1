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
@Table(name = "course_pic")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CoursePic implements Serializable {
    private static final long serialVersionUID = -916357110051689486L;

    @Id
    @GeneratedValue(generator = "jpa-assigned")
    private String courseid;
    private String pic;
}