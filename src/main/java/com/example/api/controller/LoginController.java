package com.example.api.controller;

import com.example.api.dto.UserDto;
import com.example.api.model.ApiResponse;
import com.example.api.service.LoginService;
import com.example.api.service.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginController {

  @Autowired
  private LoginService loginService;

  @Autowired
  private RegisterService registerService;

  /**
   * 登入帳號，驗證成功後回傳 JWT Token
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserDto req) {
    String token = loginService.login(req);
    Map<String, String> data = new HashMap<>();
    data.put("token", token);
    return ResponseEntity.ok(ApiResponse.success("登入成功", data));
  }

  /**
   * 登出：將當前 Token 加入黑名單
   */
  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      loginService.logout(token);
    }
    return ResponseEntity.ok(ApiResponse.success("登出成功"));
  }


  /**
   * 註冊帳號
   */
  @PostMapping("/create")
  public void create(@RequestBody UserDto req) {
    registerService.create(req);
  }

  /**
   * 提供Angular asyncValidators驗證API : 檢查mail是否重複
   */
  @GetMapping("/checkEmail")
  public ResponseEntity<?> checkEmail(@RequestParam String email) {
    Map<String, Object> response = new HashMap<>();
    if (registerService.checkEmail(email)) {
      response.put("exists", true);
    }
    return ResponseEntity.ok(response);
  }
}
