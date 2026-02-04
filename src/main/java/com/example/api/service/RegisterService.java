package com.example.api.service;

import com.example.api.config.exception.GlobalException;
import com.example.api.config.exception.ResponseStatus;
import com.example.api.dto.UserDto;
import com.example.api.generate.po.UserEntity;
import com.example.api.mappers.UserMapper;
import com.example.api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterService {

  @Autowired
  private UserMapper userMapper;

  public boolean checkEmail(String email) {
    return userMapper.checkEmail(email) > 0;
  }

  public void create(UserDto user) {

    UserEntity userEntity = userMapper.findByAccount(user.getAccount());

    if (userEntity != null) {
      throw new GlobalException(ResponseStatus.REPEAT_ACCOUNT.getMessage());
    }

    String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
    UserEntity entity = new UserEntity();
    entity.setAccount(user.getAccount());
    entity.setEmail(user.getEmail());
    entity.setPassword(hashedPassword);
    userMapper.create(entity);
  }
}
