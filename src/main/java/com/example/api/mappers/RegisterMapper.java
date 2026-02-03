package com.example.api.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegisterMapper {

  @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
  int checkEmail(String email);
}
