package com.szk.blog.vo;

import lombok.Data;

/**
 * @program: blog
 * @description: 博客查询的封装类
 * @author: sunzhongkai
 * @create: 2019-11-20 11:40
 **/
@Data
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;

    public BlogQuery() {
    }
}
