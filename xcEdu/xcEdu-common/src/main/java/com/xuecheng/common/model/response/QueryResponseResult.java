package com.xuecheng.common.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 23:01
 * @version: V1.0
 * @Description: cms（页面、站点、模板）查询响应模型
 */
@Data
@ToString
public class QueryResponseResult extends ResponseResult {
    QueryResult queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }
}