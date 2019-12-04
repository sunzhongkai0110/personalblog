package com.szk.blog.service;

import com.szk.blog.dao.UserRepository;
import com.szk.blog.entity.User;
import com.szk.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: blog
 * @description: USerService接口的实现类
 * @author: sunzhongkai
 * @create: 2019-11-16 22:47
 **/
@Service
public class UserServiceImpl implements UserService {

    /**
     * 注入UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * 检查用户名和登录密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
