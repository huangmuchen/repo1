package com.xuecheng.common.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 16:51
 * @version: V1.0
 * @Description: 封装查询结果
 */
@Data
@ToString
public class QueryResult<T> {
    // 数据列表
    private List<T> list;
    // 数据总数
    private long total;
}