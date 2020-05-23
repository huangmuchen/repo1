package com.xuecheng.common.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 23:01
 * @version: V1.0
 * @Description: 响应结果类型
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