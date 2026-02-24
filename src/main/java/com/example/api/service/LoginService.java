package com.example.api.service;

import com.example.api.config.exception.GlobalException;
import com.example.api.config.exception.ResponseStatus;
import com.example.api.dto.UserDto;
import com.example.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private TokenBlacklistService tokenBlacklistService;

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

  /**
   * 登出：將 Token 加入黑名單，並清除當前的認證資訊
   */
  public void logout(String token) {
    tokenBlacklistService.add(token, jwtUtil.getExpiration(token));
    SecurityContextHolder.clearContext();
  }
}
