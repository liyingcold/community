package com.ly.community.controller;

import com.ly.community.dto.PaginationDTO;
import com.ly.community.dto.QuestionDTO;
import com.ly.community.mapper.QuestionMapper;
import com.ly.community.mapper.UserMapper;
import com.ly.community.model.Question;
import com.ly.community.model.User;
import com.ly.community.service.QuestionService;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
                        ){
//        请求cookie
        Cookie[] cookies=request.getCookies();
        if (cookies !=null && cookies.length !=0){
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
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);

//        访问indexController时，先注入UserMapper,UserMapper才可以访问数据库的user
        return "index";
    }
}
