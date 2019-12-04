package com.szk.blog.aspect;

import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: blog
 * @description: 日志处理的切面(AOP)
 * @author: sunzhongkai
 * @create: 2019-11-12 21:13
 **/
@Aspect
@Component//组件扫描，springboot找到此对象
public class LogAspect {
    /**
     * 获取字符串日志记录
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义一个切面
     */
    @Pointcut("execution(* com.szk.blog.web.*.*(..))")
    public void log() {
    }

    /**
     * 在切面之前执行
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //传递参数
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        //通过传入参数joinPoint获取类名和方法名
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+" , "+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        RequestStLog requestStLog=new RequestStLog(url,ip,classMethod,args);
        logger.info("Request:{}",requestStLog);
    }

    /**
     * 在切面之后执行
     */
    @After("log()")
    public void doAfter() {
        logger.info("-----------doAfter----------------");
    }

    /**
     * 记录返回的内容
     * 在方法执行完返回的时候执行
     */
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result:{}", result);
    }

    /**
     * 封装成内部类
     */
    @Data
    private class RequestStLog{
        //通过HttpRequest获取
        private String url;
        private String ip;
        //调用哪个类哪个方法
        private String classMethod;
        //请求参数
        private Object[] args;

        public RequestStLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }
    }
}
