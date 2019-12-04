package com.szk.blog.web.admin;

import com.szk.blog.entity.Blog;
import com.szk.blog.entity.User;
import com.szk.blog.service.BlogService;
import com.szk.blog.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description: 登录拦截器
 * @author: sunzhongkai
 * @create: 2019-11-17 16:13
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String RETDIRECT_LIST="redirect:/admin/blogs";

    /**
     * 注入BlogService,TypeService,TagService
     */
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 访问blog页面
     * @param pageable 指定分页的方式,比如：分页的大小,排序
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        //拿到查找出来的对象的一组数据
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    /**
     * 使用ajax和thymeleaf模板对页面局部进行动态刷新
     * @param pageable 指定分页的方式,比如：分页的大小,排序
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        //拿到查找出来的对象的一组数据
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        //返回admin文件夹下的blogs页面下的blogList片段
        return "admin/blogs :: blogList";
    }

    /**
     * 跳转到发博客的页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        //标签的初始化
        //model.addAttribute("types",typeService.listType());
        //初始化标签
        //model.addAttribute("tags",tagService.listTag());
        //和修改共用一个页面时，返回值报错
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    /**
     * 设置分类和标签初始化
     * @param model
     */
    private void setTypeAndTag(Model model){
        //标签的初始化
        model.addAttribute("types",typeService.listType());
        //初始化标签
        model.addAttribute("tags",tagService.listTag());
    }

    /**
     * 修改博客
     * @param id 根据id获取blog对象
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        //标签的初始化
        //model.addAttribute("types",typeService.listType());
        //初始化标签
        //model.addAttribute("tags",tagService.listTag());
        Blog blog=blogService.findBlog(id);
        blog.init();
        //和修改共用一个页面时，返回值报错
        model.addAttribute("blog",blog);
        return INPUT;
    }

    /**
     * 新增时候的提交
     * @param blog 新增的博客
     * @param attributes
     * @param session 传递session中的user
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        //从session对象中取出user进行初始化
        blog.setUser((User) session.getAttribute("user"));
        //根据页面传过来的type.id在blog中创建一个type,存在typeId,根据id在typeService中查询type
        blog.setType(typeService.findType(blog.getType().getId()));
        //
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(blog.getId()==null){
            b=blogService.insertBlog(blog);
        }else{
            b=blogService.updateBlog(blog.getId(),blog);
        }
        if (b==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return RETDIRECT_LIST;
    }

    /**
     * 删除操作
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return RETDIRECT_LIST;
    }
}
