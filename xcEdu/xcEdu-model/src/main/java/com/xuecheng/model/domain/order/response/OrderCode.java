package com.xuecheng.model.domain.order.response;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.common.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@ToString
@AllArgsConstructor
public enum OrderCode implements ResultCode {
    ORDER_ADD_ITEMISNULL(false, 41001, "创建订单未选择课程！"),
    ORDER_ADD_ORDERNUMERROR(false, 41002, "生成订单号错误！"),
    ORDER_ADD_GETCOURSEERROR(false, 41003, "找不到课程信息！"),
    ORDER_FINISH_NOTFOUNDORDER(false, 41004, "找不到订单信息！"),
    Pay_NOTFOUNDORDER(false, 41010, "找不到要支付的订单！"),
    Pay_USERERROR(false, 41011, "支付用户与订单用户不一致！"),
    Pay_NOTFOUNDPAY(false, 41012, "支付记录不存在！");

    // 操作是否成功
    @ApiModelProperty(value = "媒资系统操作是否成功", example = "true", required = true)
    boolean success;
    // 操作代码
    @ApiModelProperty(value = "媒资系统操作代码", example = "22001", required = true)
    int code;
    // 提示信息
    @ApiModelProperty(value = "媒资系统操作提示", example = "文件在系统已存在！", required = true)
    String message;

    private static final ImmutableMap<Integer, OrderCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, OrderCode> builder = ImmutableMap.builder();
        for (OrderCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}