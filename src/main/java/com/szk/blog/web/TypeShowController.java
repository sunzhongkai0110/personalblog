package com.szk.blog.web;

import com.szk.blog.entity.Type;
import com.szk.blog.service.BlogService;
import com.szk.blog.service.TypeService;
import com.szk.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: blog
 * @description: 分类展示的控制器
 * @author: sunzhongkai
 * @create: 2019-12-02 10:07
 **/
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;
    /**
     * 分页展示分类的方法
     * @param pageable
     * @param id 分类id,即typeId
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        //拿到页面中所有的分类,从大到小倒序排列
        List<Type> types=typeService.listTypeTop(10000);
        //如果id=-1,表示在导航里点进来了
        if(id==-1){
            //拿到第一个分类的id
            id=types.get(0).getId();
        }
        //使用第一个id查找博客
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        //在前端页面上显示选中未选中,把id值传回去
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
