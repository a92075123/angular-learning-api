package com.example.api.dto;

import java.util.Date;
import lombok.Data;


@Data
public class TodoDto {

  private Long id;

  private String todotitle;

  private String todocontent;

  private Date createdAt;

  private Date updatedAt;

  private Integer sortNo;
}
