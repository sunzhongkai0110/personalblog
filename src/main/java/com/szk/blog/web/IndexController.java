package com.szk.blog.web;

import com.szk.blog.service.BlogService;
import com.szk.blog.service.TagService;
import com.szk.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: blog
 * @description: 返回首页的控制器
 * @author: sunzhongkai
 * @create: 2019-11-12 19:56
 **/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 访问首页
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        //拿到分页的数据放到model中
        model.addAttribute("page",blogService.listBlog(pageable));
        //拿到从大到小分类的数据放到model中
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return ("index");
    }

    /**
     * 搜索框查询功能
     * @param pageable
     * @param query 搜索的关键字,使用like查询
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        //将查询的字符串返回页面
        model.addAttribute("query",query);
        return "search";
    }

    /**
     * 根据id访问博客
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.findAndConvert(id));
        return "blog";
    }

    /**
     * 博客的底部内容
     * @return
     */
    @GetMapping("/footer/newblogs")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}
