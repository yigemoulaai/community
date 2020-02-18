package com.wtu.controller;

import com.wtu.dto.QuestionDTO;
import com.wtu.mapper.QuestionMapper;
import com.wtu.model.Question;
import com.wtu.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public  String  question(@PathVariable(value = "id") Integer id,
                             Model model)
    {
        QuestionDTO questionDTO=questionService.getQuestionById(id);
        model.addAttribute(questionDTO);
        return  "questionforme";
    }
}
