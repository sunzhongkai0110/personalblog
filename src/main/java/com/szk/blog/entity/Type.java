package com.szk.blog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description: 分类类
 * @author: sunzhongkai
 * @create: 2019-11-14 21:03
 **/
@Data
@Entity
@Table(name = "t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")//后端校验
    private String name;//分类名字

    /**
     * 关系被维护端:被维护type和blog之间的关系
     * 一对多,多的一方维护
     */
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs=new ArrayList<>();

    public Type() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
