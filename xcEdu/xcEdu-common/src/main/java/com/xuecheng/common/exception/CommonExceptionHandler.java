package com.xuecheng.common.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: HuangMuChen
 * @date: 2020/6/9 7:52
 * @version: V1.0
 * @Description: 通用异常处理器(统一异常捕获类)
 */
@RestControllerAdvice // 对所有的Controller进行“切面”环绕，并向前端响应JSON格式的数据
@Slf4j
public class CommonExceptionHandler {
    // 定义map，配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    // 定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    // 在静态代码块中定义异常类型所对应的错误代码
    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseResult handlerCustomException(CustomException e) {
        // 记录日志
        log.error("catch exception:{}", e.getMessage());
        return new ResponseResult(e.getResultCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult handlerException(Exception e) {
        // 记录日志
        log.error("catch exception:{}", e.getMessage());
        // 初始化EXCEPTIONS
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();
        }
        // 从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        // 判断
        if (resultCode == null) {
            return new ResponseResult(CommonCode.SERVER_ERROR);
        } else {
            return new ResponseResult(resultCode);
        }
    }
}