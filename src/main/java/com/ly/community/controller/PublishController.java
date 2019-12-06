package com.ly.community.controller;

import com.ly.community.mapper.QuestionMapper;
import com.ly.community.mapper.UserMapper;
import com.ly.community.model.Question;
import com.ly.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller

public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            HttpServletRequest request,
            Model model){
        User user = null;

        //        请求cookie
        Cookie[] cookies=request.getCookies();
        if (cookies !=null){
//            循环cookie拿到token
            for (Cookie cookie : cookies) {
//                命中后
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
//                    访问indexController时，拿出数据库的token和cookie中的token，进行比较，
                    user=userMapper.findByToken(token);
//                    命中，将user放到session里面，由前端去判断是展示姓名还是登录
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }

        }
        if (user ==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(user.getGmtCreate());
        question.setGmtModified(user.getGmtModified());
        questionMapper.create(question);
        return "redirect:/";
    }
}
