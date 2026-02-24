package com.example.api.controller;

import com.example.api.dto.ArticleDto;
import com.example.api.model.ApiResponse;
import com.example.api.service.ArticleService;
import java.security.Principal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章控制器
 * <p>
 * GET 公開瀏覽，POST/PUT/DELETE 需要登入
 */
@Slf4j
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  /**
   * 取得所有文章列表
   * 未登入：只回傳公開文章
   * 已登入：回傳公開文章 + 自己的私人文章
   */
  @GetMapping
  public ResponseEntity<?> getAll(@AuthenticationPrincipal UserDetails userDetails) {
    List<ArticleDto> list = articleService.findAll(userDetails);
    return ResponseEntity.ok(ApiResponse.success(list));
  }

  /**
   * 取得單篇文章
   * 私人文章僅作者可查看
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetails userDetails) {
    log.info("查詢文章，id = {}", id);
    String currentUsername = userDetails != null ? userDetails.getUsername() : null;
    ArticleDto article = articleService.findById(id, currentUsername);
    return ResponseEntity.ok(ApiResponse.success(article));
  }

  /**
   * 新增文章（從 JWT Token 取得作者）
   */
  @PostMapping
  public ResponseEntity<?> create(@RequestBody ArticleDto dto, Principal principal) {
    log.info("新增文章：{}", dto.getTitle());
    String author = principal.getName();
    articleService.create(dto, author);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("新增成功"));
  }

  /**
   * 更新文章
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ArticleDto dto) {
    log.info("更新文章，id = {}", id);
    articleService.update(id, dto);
    return ResponseEntity.ok(ApiResponse.success("更新成功"));
  }

  /**
   * 刪除文章
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("刪除文章，id = {}", id);
    articleService.delete(id);
    return ResponseEntity.ok(ApiResponse.success("刪除成功"));
  }
}
