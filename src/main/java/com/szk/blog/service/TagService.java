package com.szk.blog.service;

import com.szk.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: blog
 * @description: 标签管理接口
 * @author: sunzhongkai
 * @create: 2019-11-18 19:20
 **/
public interface TagService {
    /**
     * 新增标签
     * @param tag
     * @return
     */
    Tag insertTag(Tag tag);

    /**
     * 查找标签
     * @param id
     * @return
     */
    Tag findTag(Long id);

    /**
     * 根据名字查找标签
     * @param name
     * @return
     */
    Tag findTagByName(String name);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Tag> listTag(Pageable pageable);

    /**
     * 标签下拉菜单查询所有标签
     * @return
     */
    List<Tag> listTag();

    /**
     * 根据字符串查询tagId
     * @param ids
     * @return
     */
    List<Tag> listTag(String ids);

    /**
     * 从大到小列出标签
     * @param size
     * @return
     */
    List<Tag> listTagTop(Integer size);

    /**
     * 修改标签
     * @param id
     * @param tag
     * @return
     */
    Tag updateTag(Long id,Tag tag);

    /**
     * 删除标签
     * @param id
     */
    void deleteTag(Long id);

}
