package com.xuecheng.model.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 数据模型项目
 */
@Data
@ToString
public class CmsConfigModel {
    private String key; // 主键
    private String name; // 项目名称
    private String url; // 项目url
    private Map mapValue; // 项目复杂值
    private String value; // 项目简单值
}