package com.szk.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: blog
 * @description: 博客实体类
 * @author: sunzhongkai
 * @create: 2019-11-14 20:50
 **/
@Data
@Entity//和数据库对应的字段
@Table(name = "t_blog")
public class Blog {
    @Id//主键
    @GeneratedValue//生成策略：自动生成
    private Long id;

    private String title;//标题

    @Basic(fetch = FetchType.LAZY)
    @Lob//大字段类型
    private String content;//内容
    private String firstPicture;//首图
    private String flag;//标记
    private Integer views;//浏览次数
    private boolean appreciation;//赞赏是否开启
    private boolean shareStatement;//分享、转载是否开启
    private boolean commentabled;//评论是否开启
    private boolean published;//版权
    private boolean recommend;//推荐是否开启
    @Temporal(TemporalType.TIMESTAMP)//数据库中生成相对应的时间
    private Date createTime;//创建时间
    @Temporal(TemporalType.TIMESTAMP)//数据库中生成相对应的时间
    private Date updateTime;//更新时间

    /**
     * 一个blog有一个type
     * 一个type有多个blog
     * 只有一个type对象
     * blog端是多的一端
     * 关系维护端
     */
    @ManyToOne
    private Type type;

    /**
     * blog是维护关系
     * 设置级连关系：新增博客的同时新增标签
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags=new ArrayList<>();

    /**
     * 多个blog对应一个user
     * 关系的被维护方
     */
    @ManyToOne
    private User user;

    /**
     * 一个blog对应多个comment
     * 关系维护方
     */
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments=new ArrayList<>();

    @Transient//不会进入数据库进行操作
    private String tagIds;

    /**
     * 博客描述
     */
    private String description;

    public Blog() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * 对tagIds进行初始化的方法
     */
    public void init(){
        this.tagIds=tagsToIds(this.getTags());
    }

    /**
     * 把tags数组转换成用逗号分隔的字符串的方法
     */
    private String tagsToIds(List<Tag> tags){
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            //开关
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

}
