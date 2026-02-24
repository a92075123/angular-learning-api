package com.example.api.dto;

import java.util.Date;
import lombok.Data;

@Data
public class ArticleDto {

  private Long id;

  private String title;

  private String content;

  private String summary;

  private String author;

  private String category;

  private Integer view_count;

  private String visibility;

  private Date created_at;

  private Date updated_at;
}
