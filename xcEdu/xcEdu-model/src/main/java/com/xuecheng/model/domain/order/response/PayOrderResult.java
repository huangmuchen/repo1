package com.xuecheng.model.domain.order.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import com.xuecheng.model.domain.order.XcOrdersPay;
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
public class PayOrderResult extends ResponseResult {
    private XcOrdersPay xcOrdersPay;
    private String orderNumber;
    private String codeUrl;    // 当tradeState为NOTPAY（未支付）时显示支付二维码
    private Float money;

    public PayOrderResult(ResultCode resultCode) {
        super(resultCode);
    }

    public PayOrderResult(ResultCode resultCode, XcOrdersPay xcOrdersPay) {
        super(resultCode);
        this.xcOrdersPay = xcOrdersPay;
    }
}