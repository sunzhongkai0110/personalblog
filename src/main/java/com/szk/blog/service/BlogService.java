package com.szk.blog.service;

import com.szk.blog.entity.Blog;
import com.szk.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @program: blog
 * @description: 博客管理的接口
 * @author: sunzhongkai
 * @create: 2019-11-19 19:19
 **/
public interface BlogService {
    /**
     * 查找博客
     * @param id
     * @return
     */
    Blog findBlog(Long id);

    /**
     * 获取blog内容,将markdown内容转成html内容
     * @param id
     * @return
     */
    Blog findAndConvert(Long id);

    /**
     * 分页查询博客
     * @param pageable
     * @param blog
     * @return
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(Pageable pageable);

    /**
     * 根据指定字符串进行查询
     * @param query
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(String query,Pageable pageable);

    /**
     * 标签页展示,根据tagId查询博客
     * @param tagId
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    /**
     * 列出一定数量的博客
     * @param size
     * @return
     */
    List<Blog> listRecommendBlogTop(Integer size);

    /**
     * 对博客进行归档,按照年份归档博客
     * @return
     */
    Map<String,List<Blog>> archiveBlog();

    /**
     * 归档页面统计博客数目
     * @return
     */
    Long countBlog();

    /**
     * 新增博客
     * @param blog
     * @return
     */
    Blog insertBlog(Blog blog);

    /**
     * 修改博客
     * @param id
     * @param blog
     * @return
     */
    Blog updateBlog(Long id,Blog blog);

    /**
     * 删除博客
     * @param id
     */
    void deleteBlog(Long id);
}
