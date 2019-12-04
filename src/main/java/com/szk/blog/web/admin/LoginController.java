package com.szk.blog.web.admin;

import com.szk.blog.entity.User;
import com.szk.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description: 登陆的控制器
 * @author: sunzhongkai
 * @create: 2019-11-16 23:23
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {
    /**
     * UserService注入
     */
    @Autowired
    private UserService userService;
    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 登录提交时传递用户名密码
     * @param username 用户名
     * @param password 密码
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes){
        User user=userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);//页面不要拿到密码
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 注销当前登陆的用户
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
