package com.szk.blog.dao;

import com.szk.blog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: blog
 * @description: 评论管理连接数据库
 * @author: sunzhongkai
 * @create: 2019-12-01 10:25
 **/
public interface CommentRepository extends JpaRepository<Comment,Long> {
    /**
     * 根据blogId进行查询,根据评论时间进行倒序排序
     * 还要查询父节点是否为空
     * @param blogId
     * @param sort
     * @return
     */
    List<Comment> findByBlogIdAndAndParentCommentNull(Long blogId, Sort sort);
}
