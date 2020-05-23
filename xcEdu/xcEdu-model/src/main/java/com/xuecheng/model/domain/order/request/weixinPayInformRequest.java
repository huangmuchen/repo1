package com.xuecheng.model.domain.order.request;

import com.xuecheng.common.model.request.RequestData;
import lombok.Data;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
public class weixinPayInformRequest extends RequestData {
    String return_code; // 返回状态码（SUCCESS/FAIL此字段是通信标识）
    String return_msg;// 返回信息
    String appid; // 公众账号ID
    String mch_id;// 商户号
    String nonce_str;// 随机字符串
    String sign;// 签名
    String sign_type;// 签名类型
    String result_code;// 业务结果（SUCCESS/FAIL）
    String err_code; // 错误代码
    String openid; // 用户标识（用户在商户appid下的唯一标识）
    String trade_type;// 交易类型（JSAPI、NATIVE、APP）
    String bank_type;// 付款银行
    String total_fee;// 订单金额
    String cash_fee;// 现金支付金额
    String transaction_id; // 微信支付订单号
    String out_trade_no; // 商户订单号
    String time_end; // 支付完成时间
}