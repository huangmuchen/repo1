package com.xuecheng.model.domain.order.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
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
public class PayQrcodeResult extends ResponseResult {
    private String codeUrl;
    private Float money;
    private String orderNumber;

    public PayQrcodeResult(ResultCode resultCode) {
        super(resultCode);
    }
}