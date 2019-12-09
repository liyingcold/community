package com.ly.community.service;

import com.ly.community.dto.PaginationDTO;
import com.ly.community.dto.QuestionDTO;
import com.ly.community.mapper.QuestionMapper;
import com.ly.community.mapper.UserMapper;
import com.ly.community.model.Question;
import com.ly.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
//        分页
        Integer offset = size * (page - 1);
//        每一页的列表
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();


        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
//            将question里面的值赋给questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        Integer totalCount = questionMapper.count();
//        分页
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }
//    当一个页面需要用到UserMapper、QuestionMapper就可以在service处理
}
