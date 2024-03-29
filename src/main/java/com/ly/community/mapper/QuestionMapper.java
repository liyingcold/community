package com.ly.community.mapper;

import com.ly.community.dto.QuestionDTO;
import com.ly.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified} ,#{creator}, #{tag})")
    public void create(Question question);

    @Select("select * from question LIMIT #{size} offset #{offset}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("Select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} LIMIT #{size} offset #{offset}")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("Select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId")Integer userId);


    @Select("select * from question where id = #{id}  ")
    Question getById(@Param("id") Integer id);
}
