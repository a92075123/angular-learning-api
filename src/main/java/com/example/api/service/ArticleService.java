package com.example.api.service;

import com.example.api.config.exception.GlobalException;
import com.example.api.config.exception.ResponseStatus;
import com.example.api.dto.ArticleDto;
import com.example.api.generate.po.ArticleEntity;
import com.example.api.mappers.ArticleMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文章服務層
 * <p>
 * 處理文章相關業務邏輯
 */
@Slf4j
@Service
@Transactional
public class ArticleService {

  @Autowired
  private ArticleMapper articleMapper;

  /**
   * 取得所有文章（列表用，不含 content）
   * 未登入：只回傳公開文章
   * 已登入：回傳所有公開文章 + 自己的私人文章
   */
  public List<ArticleDto> findAll(UserDetails userDetails) {
    if (userDetails == null) {
      return articleMapper.findAllPublic();
    }
    return articleMapper.findAllWithPrivate(userDetails.getUsername());
  }

  /**
   * 根據 ID 查詢單篇文章，同時增加瀏覽次數
   * 私人文章僅作者可查看
   */
  public ArticleDto findById(Long id, String currentUsername) {
    ArticleDto article = articleMapper.findById(id);
    if (article == null) {
      throw new GlobalException(ResponseStatus.ARTICLE_NOT_FOUND.getMessage());
    }
    // 私人文章權限檢查：僅作者可查看
    if ("PRIVATE".equals(article.getVisibility())) {
      if (currentUsername == null || !currentUsername.equals(article.getAuthor())) {
        throw new GlobalException(ResponseStatus.ARTICLE_NOT_FOUND.getMessage());
      }
    }
    articleMapper.incrementViewCount(id);
    article.setView_count(article.getView_count() + 1);
    return article;
  }

  /**
   * 新增文章
   */
  public void create(ArticleDto dto, String author) {
    ArticleEntity entity = new ArticleEntity();
    entity.setTitle(dto.getTitle());
    entity.setContent(dto.getContent());
    entity.setSummary(dto.getSummary());
    entity.setCategory(dto.getCategory());
    entity.setAuthor(author);
    entity.setVisibility(dto.getVisibility() != null ? dto.getVisibility() : "PUBLIC");
    int count = articleMapper.insert(entity);
    if (count == 0) {
      throw new GlobalException(ResponseStatus.INSERT_ERROR.getMessage());
    }
  }

  /**
   * 更新文章
   */
  public void update(Long id, ArticleDto dto) {
    ArticleDto existing = articleMapper.findById(id);
    if (existing == null) {
      throw new GlobalException(ResponseStatus.ARTICLE_NOT_FOUND.getMessage());
    }
    ArticleEntity entity = new ArticleEntity();
    entity.setId(id);
    entity.setTitle(dto.getTitle());
    entity.setContent(dto.getContent());
    entity.setSummary(dto.getSummary());
    entity.setCategory(dto.getCategory());
    entity.setVisibility(dto.getVisibility() != null ? dto.getVisibility() : existing.getVisibility());
    int count = articleMapper.update(entity);
    if (count == 0) {
      throw new GlobalException(ResponseStatus.UPDATE_ERROR.getMessage());
    }
  }

  /**
   * 刪除文章
   */
  public void delete(Long id) {
    log.info("刪除文章，id = {}", id);
    int rows = articleMapper.deleteById(id);
    if (rows == 0) {
      throw new GlobalException(ResponseStatus.DELETE_ERROR.getMessage());
    }
  }
}
