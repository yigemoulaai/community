package com.wtu.controller;


import com.wtu.mapper.QuestionMapper;
import com.wtu.mapper.UserMapper;
import com.wtu.model.Question;
import com.wtu.model.User;
import com.wtu.service.QuestionService;
import kotlin.ExperimentalUnsignedTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public  String publish()
    {
    return "publish";
    }

    @GetMapping("/publish/{id}")
    public  String edit(@PathVariable(value = "id") Integer id,
                        Model model)
    {
        Question question=questionMapper.getQuestionById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        return "publish";
    }

    @PostMapping("/publish")
    public  String doPublish(@RequestParam(value = "title",required = false) String title,
                             @RequestParam(value = "description",required = false) String description,
                             @RequestParam(value = "tag" ,required = false) String tag,
                             @RequestParam(value = "id",required = false) Integer id,
                             HttpServletRequest request,
                             Model model) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);
        if(title==null|| title=="")
        {
            model.addAttribute("error","标题不能为空");
            return  "publish";
        }
        if(description==null|| description=="")
        {
            model.addAttribute("error","描述不能为空");
            return  "publish";
        }
        if(tag==null|| tag=="")
        {
            model.addAttribute("error","标签不能为空");
            return  "publish";
        }

        User user= (User) request.getSession().getAttribute("user");
        if(user==null)
        {
            return "redirect:/";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccountId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModify(question.getGmtCreate());
        question.setId(id);
        if(user==null)
        {
            model.addAttribute("error","用户未登录");
            return  "index";

        }
        else {
            questionService.CreateOrUpdateQuestion(question);
            model.addAttribute("error","发布成功");
            return "publish";
        }
    }
}
