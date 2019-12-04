package com.szk.blog.dao;

import com.szk.blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: sunzhongkai
 * @create: 2019-11-18 19:19
 **/
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * 在数据库中根据name查询Tag
     * @param name
     * @return
     */
    Tag findByName(String name);

    /**
     * 根据分页对象中的排序进行选取从大到小取前几个
     */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
