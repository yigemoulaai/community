package com.wtu.advice;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//@ControllerAdvice
public class CustomizeExceptionHandler {
   //@ExceptionHandler(Exception.class)

    ModelAndView handler(HttpServletRequest request, Throwable ex, Model model)
    {
        HttpStatus status=getStatus(request);
        if(ex instanceof  CustomizeException)
        {
            model.addAttribute("message", ex.getMessage());
        }
        else {
            model.addAttribute("message","服务器异常");
        }

        return  new ModelAndView("error");
    }
    private  HttpStatus getStatus(HttpServletRequest request)
    {
        Integer statuscode=(Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statuscode==null)
        {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return  HttpStatus.valueOf(statuscode);
    }

}
