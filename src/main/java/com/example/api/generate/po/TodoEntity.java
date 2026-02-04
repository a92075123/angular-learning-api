package com.example.api.generate.po;

import java.util.Date;

public class TodoEntity {

  private Long id;

  private String todotitle;

  private String todocontent;

  private Date createdAt;

  private Date updatedAt;

  private Integer sortNo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTodotitle() {
    return todotitle;
  }

  public void setTodotitle(String todotitle) {
    this.todotitle = todotitle == null ? null : todotitle.trim();
  }

  public String getTodocontent() {
    return todocontent;
  }

  public void setTodocontent(String todocontent) {
    this.todocontent = todocontent == null ? null : todocontent.trim();
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Integer getSortNo() {
    return sortNo;
  }

  public void setSortNo(Integer sortNo) {
    this.sortNo = sortNo;
  }
}