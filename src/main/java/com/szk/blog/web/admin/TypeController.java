package com.szk.blog.web.admin;

import com.szk.blog.entity.Type;
import com.szk.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @program: blog
 * @description: 分类管理的控制器
 * @author: sunzhongkai
 * @create: 2019-11-18 10:17
 **/
@Controller
@RequestMapping("/admin")
public class TypeController {
    /**
     * 注入TypeService
     */
    @Autowired
    private TypeService typeService;

    /**
     * 分类页的访问方法
     * @param pageable 根据前端页面构造好的参数,自动封装到Pageable对象中来
     * @param model 前端页面展示的模型
     * @return
     */
    @GetMapping("/types")
    public String list(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 新增页面
     * @param model 后端校验时,要往model中添加type,不然会报错
     * @return 新增页面
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "/admin/types-input";
    }

    /**
     * 共用一个页面,进行分类名称的编辑
     * @PathVariable 保证Long类型的id和注解中的id一致
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.findType(id));
        return "/admin/types-input";
    }

    /**
     * 删除分类名称
     * @param id
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

    /**
     * 通过post方法提交新增分类表单内容
     * @param type types-input.html中分类名称的name属性
     * @param result 接收校验的结果,Type和BindingResult一定要挨着,不然校验没效果
     * @param attributes 拿到列表页面的消息
     * @return 重定向的分类页面
     * @Valid 校验type对象
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1=typeService.findTypeByName(type.getName());
        /**
         * 如果新增项和分类中原有项重名
         * 根据name值查数据库,如果存在,则校验不通过
         */
        if(type1 != null){
            result.rejectValue("name","nameError","不能添加重复分类");//加一条验证的结果
        }
        /**
         * 如果提交分类名称未填写,出错,返回输入页面
         */
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.insertType(type);
        /**
         * 新增失败,给出提示
         * 新增成功,给出提示
         */
        if (t==null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改分类名称提交表单
     * @param type types-input.html中分类名称的name属性
     * @param result 接收校验的结果,Type和BindingResult一定要挨着,不然校验没效果
     * @param attributes 拿到列表页面的消息
     * @return 重定向的分类页面
     * @Valid 校验type对象
     * @PathVariable 接收id
     */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Type type1=typeService.findTypeByName(type.getName());
        /**
         * 如果新增项和分类中原有项重名
         * 根据name值查数据库,如果存在,则校验不通过
         */
        if(type1 != null){
            result.rejectValue("name","nameError","不能添加重复分类");//加一条验证的结果
        }
        /**
         * 如果提交分类名称未填写,出错,返回输入页面
         */
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.updateType(id,type);
        /**
         * 新增失败,给出提示
         * 新增成功,给出提示
         */
        if (t==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

}
