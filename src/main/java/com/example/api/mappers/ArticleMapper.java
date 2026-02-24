package com.example.api.mappers;

import com.example.api.dto.ArticleDto;
import com.example.api.generate.po.ArticleEntity;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Article Mapper 介面
 * <p>
 * 使用 MyBatis 註解方式定義 SQL
 */
@Mapper
public interface ArticleMapper {

  /** 查詢所有公開文章（未登入使用者） */
  @Select("SELECT id, title, summary, author, category, visibility, view_count, created_at, updated_at "
      + "FROM articles WHERE visibility = 'PUBLIC' ORDER BY created_at DESC")
  List<ArticleDto> findAllPublic();

  /** 查詢所有公開文章 + 指定作者的私人文章（已登入使用者） */
  @Select("SELECT id, title, summary, author, category, visibility, view_count, created_at, updated_at "
      + "FROM articles WHERE visibility = 'PUBLIC' OR author = #{author} ORDER BY created_at DESC")
  List<ArticleDto> findAllWithPrivate(@Param("author") String author);

  @Select("SELECT * FROM articles WHERE id = #{id}")
  ArticleDto findById(@Param("id") Long id);

  @Insert(
      "INSERT INTO articles (title, content, summary, author, category, visibility, created_at, updated_at) "
          + "VALUES (#{title}, #{content}, #{summary}, #{author}, #{category}, #{visibility}, NOW(), NOW())")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(ArticleEntity article);

  @Update("UPDATE articles SET title = #{title}, content = #{content}, summary = #{summary}, "
      + "category = #{category}, visibility = #{visibility}, updated_at = NOW() WHERE id = #{id}")
  int update(ArticleEntity article);

  @Delete("DELETE FROM articles WHERE id = #{id}")
  int deleteById(@Param("id") Long id);

  @Update("UPDATE articles SET view_count = view_count + 1 WHERE id = #{id}")
  int incrementViewCount(@Param("id") Long id);
}
