package com.example.api.mappers;

import com.example.api.generate.po.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
  int checkEmail(String email);

  void create(UserEntity user);

  UserEntity findByAccount(String account);
}
