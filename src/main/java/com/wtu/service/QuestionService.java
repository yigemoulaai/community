package com.wtu.service;

import com.wtu.dto.PaginationDTO;
import com.wtu.dto.QuestionDTO;
import com.wtu.mapper.QuestionMapper;
import com.wtu.mapper.UserMapper;
import com.wtu.model.Question;
import com.wtu.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service

public class QuestionService {
    @Autowired
   private QuestionMapper questionMapper;


    @Autowired
    private  UserMapper userMapper;

    public  void CreateOrUpdateQuestion(Question question) {
        if(question.getId()==null) {
            System.out.println("在添加");
            questionMapper.insertQuestion(question);
        }
        else
        {
            questionMapper.updateQuestion(question);
        }
    }


    public PaginationDTO getQuestionList(Integer page, Integer size) {
        Integer totalPage=0;
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalcount=questionMapper.getCount();
        if(totalcount % size==0)
        {
            totalPage=totalcount/size;
        }
        else
        {
            totalPage=totalcount/size+1;

        }
        //输入页码的容错处理
        if (page<1)
        {
            page=1;
        }
        if(page>totalPage)
        { page=totalPage;}
        Integer offset=size*(page-1);
        List<Question> questions=questionMapper.getQuestionList(offset,size);

        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for(Question question:questions)
        {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOS);

        paginationDTO.setPagination(totalcount,page,size,totalPage);

        return  paginationDTO;

    }

    public PaginationDTO getQuestionForMe(String UserId, Integer page, Integer size) {
        Integer totalPage=0;
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalcount=questionMapper.getCountForMe(UserId);
        if(totalcount % size==0)
        {
            totalPage=totalcount/size;
        }
        else
        {
            totalPage=totalcount/size+1;

        }
        //输入页码的容错处理
        if (page<1)
        {
            page=1;
        }
        if(page>totalPage)
        { page=totalPage;}

        Integer offset=size*(page-1);
        List<Question> questions=questionMapper.getQuestionForMe(UserId,offset,size);
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for(Question question:questions)
        {
           User user=userMapper.findById(question.getCreator());//此处做修改
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOS);

        paginationDTO.setPagination(totalcount,page,size,totalPage);

        return  paginationDTO;

    }

    public QuestionDTO getQuestionById(Integer id) {
        Question question=questionMapper.getQuestionById(id);
        User user=userMapper.findById(question.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question,questionDTO);
        return  questionDTO;
    }
}
