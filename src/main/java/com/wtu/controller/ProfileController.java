package com.wtu.controller;

import com.wtu.dto.PaginationDTO;
import com.wtu.mapper.QuestionMapper;
import com.wtu.model.Question;
import com.wtu.model.User;
import com.wtu.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public  String  profile(@PathVariable(name="action") String action,
                            @RequestParam(value = "page",defaultValue = "1" ) Integer page,
                            @RequestParam(value = "size",defaultValue = "5") Integer size,
                            Model model,
                            HttpServletRequest request)
    {

        User user= (User) request.getSession().getAttribute("user");
        if(user==null)
        {
            return "redirect:/";
        }
        if("question".equals(action))
        {
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的问题");

        }
        else if("reply".equals(action))
        {
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","我的回复");
        }

        PaginationDTO paginationDTO=questionService.getQuestionForMe(user.getAccountId(),page,size);
        model.addAttribute("questionFroMeList",paginationDTO);

        return  "profile";
    }
}
