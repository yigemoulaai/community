package com.wtu.controller;

import com.wtu.dto.AccessTokenDto;
import com.wtu.dto.GithubUser;
import com.wtu.model.User;
import com.wtu.mapper.UserMapper;
import com.wtu.provider.GithubProvider;
import com.wtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;
    @Value("${github.client.id}")
    private  String client_id;
    @Value("${github.client.server}")
    private  String client_server;
    @Value("${github.redirect.uri}")
    private  String redirect_uri;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        AccessTokenDto accessTokenDto=new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_server);
        accessTokenDto.setRedirect_uri(redirect_uri);
        accessTokenDto.setState("1");
        String accessToken=githubProvider.getAccessToken(accessTokenDto);
        GithubUser user=githubProvider.getUser(accessToken);
        System.out.println(user.getName()+user.getId());
        if(user!=null)
        {
            //登录成功，设置session
            User user1=new User();
            user1.setName(user.getName());
            user1.setAccountId(String.valueOf(user.getId()));
            String token = UUID.randomUUID().toString();
            user1.setToken(token);
            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user1.getGmtCreate());
            user1.setAvatarUri(user.getAvatar_url());
            userService.createOrUpdate(user1);
            response.addCookie(new Cookie("token",token));
            request.getSession().setAttribute("user",user1);
            return  "redirect:/publish";
        }
        else {
            //登录失败
            return  "redirect:/";
        }

    }
    @GetMapping("/logout")
    public  String logout(HttpServletRequest request,
                          HttpServletResponse response)
    {
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        return  "redirect:/";
    }
}
