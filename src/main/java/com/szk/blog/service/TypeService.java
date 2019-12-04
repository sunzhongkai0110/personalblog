package com.szk.blog.service;

import com.szk.blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: blog
 * @description: 分类管理接口
 * @author: sunzhongkai
 * @create: 2019-11-17 20:11
 **/
public interface TypeService {
    /**
     * 新增分类
     * @param type
     * @return
     */
    Type insertType(Type type);

    /**
     * 查询分类
     * @param id
     * @return
     */
    Type findType(Long id);

    /**
     * 根据名称查找类型
     * @param name
     * @return
     */
    Type findTypeByName(String name);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 分类下拉菜单查询所有分类
     * @return
     */
    List<Type> listType();

    /**
     * 列出一定数量的分类
     * @param size
     * @return
     */
    List<Type> listTypeTop(Integer size);

    /**
     * 修改Type
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id,Type type);

    /**
     * 删除Type
     * @param id
     */
    void deleteType(Long id);
}
