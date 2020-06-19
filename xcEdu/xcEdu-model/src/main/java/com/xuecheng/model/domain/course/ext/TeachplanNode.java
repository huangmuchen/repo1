package com.xuecheng.model.domain.course.ext;

import com.xuecheng.model.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 课程计划结点类
 */
@Data
@ToString
public class TeachplanNode extends Teachplan {
    List<TeachplanNode> children; // 所有节点集合
}