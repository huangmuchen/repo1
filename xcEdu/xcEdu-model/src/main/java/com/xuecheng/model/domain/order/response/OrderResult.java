package com.xuecheng.model.domain.order.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import com.xuecheng.model.domain.order.XcOrders;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class OrderResult extends ResponseResult {
    private XcOrders xcOrders;

    public OrderResult(ResultCode resultCode, XcOrders xcOrders) {
        super(resultCode);
        this.xcOrders = xcOrders;
    }
}