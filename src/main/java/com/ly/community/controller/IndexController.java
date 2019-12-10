package com.ly.community.controller;

import com.ly.community.dto.PaginationDTO;
import com.ly.community.mapper.UserMapper;
import com.ly.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    /**
     *
     * @param model
     * @param page 每一页的页码
     * @param size 每一页的分页数
     * @return
     */
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
                        ){

//        传递两个参数，传入service
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);

//        访问indexController时，先注入UserMapper,UserMapper才可以访问数据库的user
        return "index";
    }
}
