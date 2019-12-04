package com.szk.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: blog
 * @description: 评论类
 * @author: sunzhongkai
 * @create: 2019-19-11-14 下午9:11
 **/
@Data
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;//昵称
    private String email;//邮箱
    private String content;//评论内容
    private String avatar;//头像地址
    @Temporal(TemporalType.TIMESTAMP)//数据库中生成相对应的时间
    private Date createTime;//创建时间

    /**
     * 多的一端是关系被维护方
     */
    @ManyToOne
    private Blog blog;

    /**
     * 一个父对象可以有多个子对象
     */
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments=new ArrayList<>();

    /**
     * 多个评论可以都评论一个父类
     */
    @ManyToOne
    private Comment parentComment;

    private boolean adminComment;

    public Comment() {
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }
}
