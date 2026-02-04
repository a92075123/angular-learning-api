package com.example.api.service;

import com.example.api.config.exception.GlobalException;
import com.example.api.config.exception.ResponseStatus;
import com.example.api.dto.UserDto;
import com.example.api.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public LoginService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  public String login(UserDto user) {
    try {
      /*
        UsernamePasswordAuthenticationToken:封裝成認證請求對象
        authenticate: 核心驗證 呼叫 UserDetailsService 的實現類
       */
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword()));
    } catch (AuthenticationException e) {
      throw new GlobalException(ResponseStatus.PASSWORD_ERROR.getMessage());
    }

    return jwtUtil.generateToken(user.getAccount());
  }
}
