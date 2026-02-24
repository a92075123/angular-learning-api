package com.example.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expiration;

  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
  }

  /**
   * 根據帳號產生 JWT Token
   */
  public String generateToken(String account) {
    return Jwts.builder()
        .subject(account)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(secretKey)
        .compact();
  }

  /**
   * 從 Token 中取出帳號
   */
  public String extractAccount(String token) {
    return extractClaims(token).getSubject();
  }

  /**
   * 取得 Token 的過期時間
   */
  public Date getExpiration(String token) {
    return extractClaims(token).getExpiration();
  }

  /**
   * 驗證 Token 是否有效（未過期、簽名正確）
   */
  public boolean isTokenValid(String token) {
    try {
      Claims claims = extractClaims(token);
      return !claims.getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  private Claims extractClaims(String token) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
