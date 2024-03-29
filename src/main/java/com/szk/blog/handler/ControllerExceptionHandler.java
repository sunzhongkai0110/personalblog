package com.szk.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: blog
 * @description: 自定义错误的拦截器
 * @author: sunzhongkai
 * @create: 2019-11-12 20:04
 **/
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * 获取日志
     */
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 处理异常信息
     * @param request 通过request知道访问的url
     * @param e 错误信息
     * @return 错误页面
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        /**
         * 记录异常信息，在控制台输出
         */
        logger.error("Request URL:{}, Exception:{}",request.getRequestURL(),e);

        /**
         * 如果通过注解发现异常，抛出异常，让springboot处理
         */
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }

        /**
         * 可视化页面
         */
        ModelAndView mv=new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        /**
         * 返回哪个页面
         */
        mv.setViewName("error/error");
        return mv;
    }

}
