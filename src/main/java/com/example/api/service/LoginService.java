package com.example.api.service;

import com.example.api.config.exception.GlobalException;
import com.example.api.config.exception.ResponseStatus;
import com.example.api.dto.UserDto;
import com.example.api.generate.po.UserEntity;
import com.example.api.mappers.UserMapper;
import com.example.api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  @Autowired
  private UserMapper userMapper;

  public UserEntity login(UserDto user) {
    UserEntity entity = userMapper.findByAccount(user.getAccount());

    if (entity == null) {
      throw new GlobalException(ResponseStatus.NOT_ACCOUNT.getMessage());
    }

    if (!PasswordUtil.verifyPassword(user.getPassword(), entity.getPassword())) {
      throw new GlobalException(ResponseStatus.PASSWORD_ERROR.getMessage());
    }

    return entity;
  }
}
