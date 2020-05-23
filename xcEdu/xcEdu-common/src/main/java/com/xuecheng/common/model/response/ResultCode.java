package com.xuecheng.common.model.response;

/**
 * @author: HuangMuChen
 * @date: 2020/5/23 16:51
 * @version: V1.0
 * @Description: 响应状态码：10000-- 通用错误代码，22000-- 媒资错误代码，23000-- 用户中心错误代码，24000-- cms错误代码，25000-- 文件系统
 */
public interface ResultCode {

    /**
     * 操作是否成功,true为成功，false操作失败
     *
     * @return
     */
    boolean success();

    /**
     * 操作代码
     *
     * @return
     */
    int code();

    /**
     * 提示信息
     *
     * @return
     */
    String message();
}