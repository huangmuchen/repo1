package com.xuecheng.model.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 请求模型：封装前端课程搜索参数
 */
@Data
@ToString
@ApiModel(value = "CourseSearchParam", description = "封装搜索条件")
public class CourseSearchParam implements Serializable {
    @ApiModelProperty("关键字")
    String keyword;
    @ApiModelProperty("一级分类")
    String mt;
    @ApiModelProperty("二级分类")
    String st;
    @ApiModelProperty("难度等级")
    String grade;
    @ApiModelProperty("价格区间：最低价")
    Float price_min;
    @ApiModelProperty("价格区间：最高价")
    Float price_max;
    @ApiModelProperty("排序字段")
    String sort;
    @ApiModelProperty("过虑字段")
    String filter;
}