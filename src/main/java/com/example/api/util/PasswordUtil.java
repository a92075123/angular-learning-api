package com.example.api.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

  // 註冊時：將密碼雜湊後再存入資料庫
  public static String hashPassword(String plainPassword) {
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
  }

  // 登入時：驗證密碼是否正確
  public static boolean verifyPassword(String plainPassword, String hashedPassword) {
    return BCrypt.checkpw(plainPassword, hashedPassword);
  }
}
