package com.xuecheng.model.domain.search;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class CourseSearchParam implements Serializable {
    String keyword; // 关键字
    String mt; // 一级分类
    String st; // 二级分类
    String grade; // 难度等级
    Float price_min; // 价格区间
    Float price_max;
    String sort; // 排序字段
    String filter; // 过虑字段
}