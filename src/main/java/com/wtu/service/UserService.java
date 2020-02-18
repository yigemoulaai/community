package com.wtu.service;

import com.wtu.mapper.UserMapper;
import com.wtu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User user1=null;
       user1= userMapper.findById(user.getAccountId());
       if(user1==null)
       {
           userMapper.insertUser(user);
       }
       else
       {
           userMapper.update(user);
       }
    }
}
