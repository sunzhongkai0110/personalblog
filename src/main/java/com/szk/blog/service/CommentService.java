package com.szk.blog.service;

import com.szk.blog.entity.Comment;

import java.util.List;

/**
 * @program: blog
 * @description: 评论提交的接口
 * @author: sunzhongkai
 * @create: 2019-12-01 10:19
 **/
public interface CommentService {
    /**
     * 列出评论列表
     * @param blogId
     * @return
     */
    List<Comment> listCommentByBlogId(Long blogId);

    /**
     * 保存评论
     * @param comment
     * @return
     */
    Comment saveComment(Comment comment);
}
