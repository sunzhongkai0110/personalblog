package com.szk.blog.service;

import com.szk.blog.NotFoundException;
import com.szk.blog.dao.TagRepository;
import com.szk.blog.entity.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description: 标签管理的实现类
 * @author: sunzhongkai
 * @create: 2019-11-18 19:33
 **/
@Service
public class TagServiceImpl implements TagService {
    /**
     * 注入TagRepository
     */
    @Autowired
    private TagRepository tagRepository;

    /**
     * 新增标签
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public Tag insertTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * 查找标签
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Tag findTag(Long id) {
        return tagRepository.getOne(id);
    }

    /**
     * 根据名字查找标签
     * @param name
     * @return
     */
    @Transactional
    @Override
    public Tag findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    /**
     * 标签下拉菜单查询所有标签
     * @return
     */
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    /**
     * 根据字符串查询tagId
     * @param ids
     * @return
     */
    @Override
    public List<Tag> listTag(String ids) {
        //根据id的集合来获取对象的集合
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable= PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    /**
     * 将字符串定义成一个集合
     */
    private List<Long> convertToList(String ids) {
        //定义Long类型数组,id是Long类型
        List<Long> list = new ArrayList<>();
        //非空判断
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


    /**
     * 修改标签
     * @param id
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
