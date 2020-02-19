package com.wtu.service;

import com.wtu.mapper.UserMapper;
import com.wtu.model.User;
import com.wtu.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        List<User> user1=null;
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        user1= userMapper.selectByExample(example);
       if(user1.size()==0)
       {
           userMapper.insert(user);
       }
       else
       {
           userMapper.update(user);
       }
    }
}
