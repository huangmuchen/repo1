package com.xuecheng.common.constant;

/**
 * @author: HuangMuChen
 * @date: 2020/6/19 20:37
 * @version: V1.0
 * @Description: 课程相关常量
 */
public interface CourseConstant {
    // 课程的发布状态
    String COURSEBASE_STATUS_NO = "202001"; // 未发布
    String COURSEBASE_STATUS_YES = "202002"; // 已发布
    String COURSEBASE_STATUS_OFFLINE = "202003"; // 已下线
    // 课程计划发布状态
    String TEACHPLAN_STATUS_NO = "0"; // 未发布
    String TEACHPLAN_STATUS_YES = "1"; // 已发布
    // 课程计划等级
    String GRADE_ONE = "1"; // 一级
    String GRADE_TWO = "2"; // 二级
    String GRADE_THREE = "3"; // 三级
    // 课程计划根节点的父节点id
    String TEACHPLAN_ROOT_PARENTID = "0";
}