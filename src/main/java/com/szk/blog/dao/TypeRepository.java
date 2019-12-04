package com.szk.blog.dao;

import com.szk.blog.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: blog
 * @description: 分类管理连接数据库
 * @author: sunzhongkai
 * @create: 2019-11-17 21:38
 **/
public interface TypeRepository extends JpaRepository<Type, Long> {
    /**
     * 在数据库中根据name查询Type
     * @param name
     * @return
     */
    Type findByName(String name);

    /**
     * 根据分页对象中的排序进行选取从大到小取前几个
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
