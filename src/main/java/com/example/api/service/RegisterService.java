package com.example.api.service;

import com.example.api.mappers.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterService {

  @Autowired
  private RegisterMapper registerMapper;

  public boolean checkEmail(String email) {
    return registerMapper.checkEmail(email) > 0;
  }

}
