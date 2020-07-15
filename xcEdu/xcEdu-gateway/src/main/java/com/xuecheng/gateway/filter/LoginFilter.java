package com.xuecheng.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.gateway.config.FilterProperties;
import com.xuecheng.gateway.service.IZuulService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: HuangMuChen
 * @date: 2019/11/10 14:52
 * @version: V1.0
 * @Description: 网关登录拦截器(前置拦截器)
 */
@Component
@EnableConfigurationProperties(FilterProperties.class)
public class LoginFilter extends ZuulFilter {
    @Autowired
    private FilterProperties prop;
    @Autowired
    private IZuulService zuulService;

    /**
     * 过滤器类型：
     * pre：请求在被路由之前执行
     * routing：在路由请求时调用
     * post：在routing和errror过滤器之后调用
     * error：处理请求时发生错误调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // "pre"
    }

    /**
     * 过滤优先级:返回值越小，优先级越高
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_ERROR_FILTER_ORDER; // 0
    }

    /**
     * 是否要过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true; // 所有url都进行过滤
    }

    /**
     * 过滤逻辑：对用户的token进行校验，如果发现未登录，则进行拦截
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取zuul提供的上下文对象：也是一个域对象，作用范围是,从请求到达Zuul，一直到路由结束，返回到客户端（只在Zuul中生效，不会被带到微服务中）
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 从上下文对象中获取请求对象
        HttpServletRequest request = requestContext.getRequest();
        // 从cookie查询用户身份令牌是否存在，不存在则拒绝访问
        String jti_token = this.zuulService.getJtiTokenFromCookie(request);
        // 判断
        if (StringUtils.isBlank(jti_token)) {
            this.accessDenied(requestContext);
            return null;
        }
        // 从http header查询jwt令牌是否存在，不存在则拒绝访问
        String jwt_token = this.zuulService.getJwtTokenFromHeader(request);
        // 判断
        if (StringUtils.isBlank(jwt_token)) {
            this.accessDenied(requestContext);
            return null;
        }
        // 从Redis查询user_token令牌是否过期，过期则拒绝访问
        long expire = this.zuulService.getExpire(jti_token);
        // 判断
        if (expire < 0) {
            this.accessDenied(requestContext);
            return null;
        }
        return null;
    }

    /**
     * 拒绝访问
     *
     * @param requestContext
     */
    private void accessDenied(RequestContext requestContext) {
        // 从上下文对象中获取响应对象
        HttpServletResponse response = requestContext.getResponse();
        // 过滤该请求，不对其进行路由
        requestContext.setSendZuulResponse(false);
        // 设置响应状态码，403
        requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        // 构建响应的信息
        ResponseResult responseResult = new ResponseResult(CommonCode.UNAUTHENTICATED);
        // 转成JSON字符串
        String json = JSON.toJSONString(responseResult);
        // 设置到响应体中
        requestContext.setResponseBody(json);
        // 设置响应格式；JSON
        response.setContentType("application/json;charset=utf-8");
    }
}