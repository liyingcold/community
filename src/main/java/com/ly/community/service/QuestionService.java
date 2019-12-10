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

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
//        首先查出totalCount
        Integer totalCount = questionMapper.count();
        if (totalCount % size ==0){
            totalPage = totalCount /size;
        }else {
            totalPage = totalCount /size +1;
        }

//       容错，校验页码（<1就设置为1，>总数就设置为总数）
        if (page<1){
            page= 1;
        }
        if (page > totalPage){
            page= totalPage;
        }
//        分页(通过总数进行判断，进入setPagination)
        paginationDTO.setPagination(totalPage,page);
//        分页（计算limit）
        Integer offset = size * (page - 1);
//        每一页的列表
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
//            将question里面的值赋给questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
//        首先查出totalCount
        Integer totalCount = questionMapper.countByUserId(userId);
        if (totalCount % size ==0){
            totalPage = totalCount /size;
        }else {
            totalPage = totalCount /size +1;
        }

//       容错，校验页码（<1就设置为1，>总数就设置为总数）
        if (page<1){
            page= 1;
        }
        if (page > totalPage){
            page= totalPage;
        }
//        分页(通过总数进行判断，进入setPagination)
        paginationDTO.setPagination(totalPage,page);

//        分页（计算limit）
        Integer offset = size * (page - 1);
//        每一页的列表
        List<Question> questions=questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
//            将question里面的值赋给questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }
//    当一个页面需要用到UserMapper、QuestionMapper就可以在service处理
}
