package com.szk.blog.web;

import com.szk.blog.entity.Comment;
import com.szk.blog.entity.User;
import com.szk.blog.service.BlogService;
import com.szk.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description: 评论的控制器
 * @author: sunzhongkai
 * @create: 2019-12-01 10:05
 **/
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;
    /**
     * 根据blogId提交评论
     * @param blogId
     * @param model
     * @return 评论列表片断
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    /**
     * 提交之后接收信息
     * @param comment
     * @param session 获取用户名和登录信息
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        //设置comment中的blog对象,建立关系
        Long blogId=comment.getBlog().getId();
        //根据blogId获得blog对象设置到comment中
        comment.setBlog(blogService.findBlog(blogId));
        //获取user对象
        User user= (User) session.getAttribute("user");
        //目前是私人博客,故登录只有管理员可以登录
        if (user != null){
            //管理员登录
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            //前端页面会传过来用户的昵称,可以不用
            //comment.setNickname(user.getNickname());
        }else{
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
