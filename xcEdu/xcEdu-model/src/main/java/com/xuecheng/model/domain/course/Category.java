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
 * @Description: 课程分类实体类
 */
@Data
@ToString
@Entity
@Table(name = "category")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
// @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Category implements Serializable {
    private static final long serialVersionUID = 7013626493878528115L;

    @Id // 主键
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id; // 分类id
    private String name; // 分类名称
    private String label;  // 分类标签，默认和名称一样
    private String parentid; // 父节点id
    private String isshow; // 是否显示
    private Integer orderby; // 排序字段
    private String isleaf;  // 是否叶子节点
}