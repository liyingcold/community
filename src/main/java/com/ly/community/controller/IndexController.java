package com.ly.community.controller;

import com.ly.community.mapper.UserMapper;
import com.ly.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
//        请求cookie
        Cookie[] cookies=request.getCookies();
        if (cookies !=null){
//            循环cookie拿到token
            for (Cookie cookie : cookies) {
//                命中后
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
//                    访问indexController时，拿出数据库的token和cookie中的token，进行比较，
                    User user=userMapper.findByToken(token);
//                    命中，将user放到session里面，由前端去判断是展示姓名还是登录
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }

        }
//        访问indexController时，先注入UserMapper,UserMapper才可以访问数据库的user

        return "index";
    }
}
