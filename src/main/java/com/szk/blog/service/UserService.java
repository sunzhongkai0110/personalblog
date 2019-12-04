package com.szk.blog.service;

import com.szk.blog.entity.User;

/**
 * @program: blog
 * @description: 关于用户登录和密码输入的接口
 * @author: sunzhongkai
 * @create: 2019-11-16 22:45
 **/
public interface UserService {
    /**
     * 关于用户登录和密码
     */
    User checkUser(String username, String password);
}
