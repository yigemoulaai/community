package com.wtu.controller;

import com.wtu.dto.PaginationDTO;
import com.wtu.dto.QuestionDTO;
import com.wtu.mapper.QuestionMapper;
import com.wtu.model.Question;
import com.wtu.model.User;
import com.wtu.mapper.UserMapper;
import com.wtu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public  String hello(HttpServletResponse response,
                         HttpServletRequest request,
                         Model model,
                         @RequestParam(value = "page",defaultValue = "1") Integer page,
                         @RequestParam(value = "size",defaultValue = "5" ) Integer size)
    {

        PaginationDTO list=questionService.getQuestionList(page,size);
        model.addAttribute("questionList",list);
        return  "index";
    }
}
