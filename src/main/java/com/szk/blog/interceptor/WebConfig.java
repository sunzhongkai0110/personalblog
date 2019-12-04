package com.szk.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: blog
 * @description: 说明要拦截带有/admin的url的配置类
 * @author: sunzhongkai
 * @create: 2019-11-17 17:16
 **/
@Configuration//告诉springboot配置类
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 1.将过滤页面的网加进来
         * 2.进行匹配过滤的路径
         * 3.排除掉一些路径,比如:login
         */
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
