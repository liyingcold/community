package com.ly.community.mapper;

import com.ly.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into public.user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from public.user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from public.user where id = #{id}")
    User findById(@Param("id") Integer id);
}
