package com.szk.blog.service;

import com.szk.blog.NotFoundException;
import com.szk.blog.dao.BlogRepository;
import com.szk.blog.entity.Blog;
import com.szk.blog.entity.Type;
import com.szk.blog.util.MarkdownUtils;
import com.szk.blog.util.MyBeanUtils;
import com.szk.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @program: blog
 * @description: 博客管理的实现类
 * @author: sunzhongkai
 * @create: 2019-11-19 19:59
 **/
@Service
public class BlogServiceImpl implements BlogService {
    /**
     * 注入BlogRepository
     */
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog findBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /**
     * 获取blog内容,将markdown内容转成html内容
     * 如果service层和dao层都有 @Transactional,service层会覆盖dao层
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog findAndConvert(Long id) {
        Blog blog=blogRepository.getOne(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        //为了不把数据库中的markdown格式改变为HTML类型
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);

        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //根据blogId更新浏览次数
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            /**
             * 条件的动态组合
             * @param root 查询的对象,把blog映射成字段
             * @param criteriaQuery 查询的容器,把查询的条件放到该容器中
             * @param criteriaBuilder 设置具体某一个条件的表达式,如模糊查询like
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //组合条件放到list中
                List<Predicate> predicates = new ArrayList<>();
                //标题的验证
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    /**
                     * 组织一定条件放到list中
                     * 第一个参数：获取的title对象
                     * 第二个参数：传递过来的值
                     */
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                //分类的验证,根据type的id进行查询
                if (blog.getTypeId() != null) {
                    /**
                     * 第一个参数：获取的type对象的id
                     * 第二个参数：传递过来的id值
                     */
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                //是否推荐的验证
                if (blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                //使用criteriaQuery进行查询
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * 根据指定字符串进行查询
     * @param query
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    /**
     * 标签页展示,根据tagId查询博客
     * @param tagId
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //关联查询,当前的blog去关联其他的tag
                Join join=root.join("tags");
                //构建条件,join.get("id")：拿到的tagId,如果和传递过来的tagId相等的话,查询数据库,返回page
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    /**
     * 列出一定数量的博客
     * @param size
     * @return
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    /**
     * 对博客进行归档,按照年份归档博客
     * @return
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //获取年份
        List<String> years=blogRepository.findGroupYear();
        //循环年份,拿到数据的列表
        Map<String,List<Blog>> map=new HashMap<>();
        for (String year:years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    /**
     * 归档页面统计博客数目
     * @return
     */
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    /**
     * 新增博客时需要进行判断是修改还是新增
     * 新增和修改的不同是新增没有id
     * @param blog
     * @return
     */
    @Transactional//放到事物中
    @Override
    public Blog insertBlog(Blog blog) {
        if(blog.getId()==null){//新增
            //对新增日期和修改日期的初始化
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);//设置浏览次数为0
        }else{//修改,只需要填写更新时间
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional//放到事物中
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        //只将blog中有值的属性复制给b
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional//放到事物中
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
