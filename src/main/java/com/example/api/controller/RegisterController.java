package com.example.api.controller;

import com.example.api.service.RegisterService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegisterController {

  @Autowired
  private RegisterService registerService;

  /**
   * 取得所有待辦事項
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
