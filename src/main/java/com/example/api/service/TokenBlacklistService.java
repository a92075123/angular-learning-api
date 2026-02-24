package com.example.api.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * Token 黑名單服務
 * <p>
 * 使用記憶體儲存已登出的 JWT Token
 */
@Service
public class TokenBlacklistService {

  /** key: token, value: 過期時間 */
  private final Map<String, Date> blacklist = new ConcurrentHashMap<>();

  /** 將 Token 加入黑名單 */
  public void add(String token, Date expiration) {
    blacklist.put(token, expiration);
  }

  /** 檢查 Token 是否在黑名單中 */
  public boolean isBlacklisted(String token) {
    return blacklist.containsKey(token);
  }
}
