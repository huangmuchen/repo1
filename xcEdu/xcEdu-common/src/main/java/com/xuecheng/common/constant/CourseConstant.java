package com.xuecheng.common.constant;

/**
 * @author: HuangMuChen
 * @date: 2020/6/19 20:37
 * @version: V1.0
 * @Description: 课程相关常量
 */
public interface CourseConstant {
    String COURSEBASE_STATUS_NO = "202001"; // 课程的发布状态：未发布
    String COURSEBASE_STATUS_YES = "202002"; // 课程的发布状态：发布
    String COURSEBASE_STATUS_DOWN = "202003"; // 课程的发布状态：已下线

    String TEACHPLAN_STATUS_NO = "0"; // 课程计划发布状态：未发布
    String TEACHPLAN_STATUS_YES = "1"; // 课程计划发布状态：已发布

    String GRADE_ONE = "1"; // 课程计划等级：一级
    String GRADE_TWO = "2"; // 课程计划等级：二级
    String GRADE_THREE = "3"; // 课程计划等级：三级

    String TEACHPLAN_ROOT_PARENTID = "0"; // 课程计划根节点的父节点id
}