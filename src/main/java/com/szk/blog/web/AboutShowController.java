package com.szk.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: blog
 * @description: 关于我页面的控制器
 * @author: sunzhongkai
 * @create: 2019-12-03 22:08
 **/
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
