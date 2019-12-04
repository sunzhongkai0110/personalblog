package com.szk.blog.dao;

import com.szk.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: blog
 * @description: 用户登录连接数据库
 * @author: sunzhongkai
 * @create: 2019-11-16 23:09
 * JpaRepository<User,Long>:User操作的对象,Long:主键类型
 **/
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}
