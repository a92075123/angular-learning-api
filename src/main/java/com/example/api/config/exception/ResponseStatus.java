package com.example.api.config.exception;

public enum ResponseStatus {

  SELECT_ERROR("查詢失敗"),

  UPDATE_ERROR("更新失敗"),

  INSERT_ERROR("新增失敗"),

  DELETE_ERROR("刪除失敗"),

  NOT_ACCOUNT("此帳號沒有註冊"),

  REPEAT_ACCOUNT("此帳號以重複註冊過"),

  PASSWORD_ERROR("密碼錯誤");

  private String message;

  ResponseStatus(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
