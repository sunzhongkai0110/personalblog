package com.szk.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: blog
 * @description: 过滤关于/admin的过滤器,没有登录不能访问关于/admin的页面
 * @author: sunzhongkai
 * @create: 2019-11-17 16:59
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在请求到达目的地之前，预处理进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 判断是否是登录用户,即判断session中是否有user
         * 如果为空,重定向到的登录页面.即admin下
         */
        if(request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
