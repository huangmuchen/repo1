package com.xuecheng.course.exception;

import com.xuecheng.common.exception.CommonExceptionHandler;
import com.xuecheng.common.model.response.CommonCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: HuangMuChen
 * @date: 2020/7/14 17:46
 * @version: V1.0
 * @Description: 课程管理微服务自定义异常捕获类
 */
@RestControllerAdvice
public class CourseExceptionHandler extends CommonExceptionHandler {
    // 在静态代码块中定义异常类型所对应的错误代码
    static {
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
    }
}