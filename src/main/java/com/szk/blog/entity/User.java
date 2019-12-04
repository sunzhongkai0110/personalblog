package com.szk.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: blog
 * @description: 用户类
 * @author: sunzhongkai
 * @create: 2019-11-14 21:18
 **/
@Data
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;//昵称
    private String username;//用户名
    private String password;//密码
    private String email;//邮箱
    private String avatar;//头像
    private Integer type;//类型
    @Temporal(TemporalType.TIMESTAMP)//数据库中生成相对应的时间
    private Date createTime;//创建时间
    @Temporal(TemporalType.TIMESTAMP)//数据库中生成相对应的时间
    private Date updateTime;//更新时间

    /**
     * 一个user对应多个blog
     * 关系维护方
     */
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs=new ArrayList<>();

    public User() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
