package com.szk.blog.dao;

import com.szk.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: blog
 * @description: 博客管理连接数据库
 * @author: sunzhongkai
 * @create: 2019-11-19 20:00
 **/
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    /**
     * 根据推荐查询
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    /**
     * 根据关键字进行查询
     * @param query
     * @param pageable
     * @return
     * select * from t_blog where title like '%内容%'
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    /**
     * 根据blogId更新浏览次数
     * 如果是更新,不是查询,要加入 @Modifying
     * @param id
     * @return
     */
    @Transactional
    @Modifying
    @Query("update Blog b set b.views=b.views+1 where b.id=?1")
    int updateViews(Long id);

    /**
     * 获得倒序排序的年份
     * @return
     */
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    /**
     * 根据年份查询博客
     * @param year
     * @return
     */
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
