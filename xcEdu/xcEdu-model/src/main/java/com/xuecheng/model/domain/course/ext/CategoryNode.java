package com.xuecheng.model.domain.course.ext;

import com.xuecheng.model.domain.course.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 课程分类结点类
 */
@Data
@ToString
public class CategoryNode extends Category {
    List<CategoryNode> children; // 所有子节点集合
}