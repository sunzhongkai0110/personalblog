package com.szk.blog.service;

import com.szk.blog.NotFoundException;
import com.szk.blog.dao.TypeRepository;
import com.szk.blog.entity.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: blog
 * @description: 分类管理的实现类
 * @author: sunzhongkai
 * @create: 2019-11-17 20:17
 **/
@Service
public class TypeServiceImpl  implements TypeService {
    /**
     * 注入TypeRepository
     */
    @Autowired
    private TypeRepository typeRepository;

    /**
     * 新增分类
     * @param type
     * @return
     */
    @Transactional//放到事务中
    @Override
    public Type insertType(Type type) {
        return typeRepository.save(type);
    }


    /**
     * 查询分类
     * @param id
     * @return
     */
    @Transactional//放到事务中
    @Override
    public Type findType(Long id) {
        return typeRepository.getOne(id);
    }

    /**
     * 根据名称查找类型
     * @param name
     * @return
     */
    @Override
    public Type findTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    @Transactional//放到事务中
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    /**
     * 分类下拉菜单查询所有分类
     * @return
     */
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    /**
     * 从大到小显示前几个分类
     * @param size
     * @return
     */
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    /**
     * 修改Type
     * @param id
     * @param type
     * @return
     */
    @Transactional//放到事务中
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }

    /**
     * 删除Type
     * @param id
     */
    @Transactional//放到事务中
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
