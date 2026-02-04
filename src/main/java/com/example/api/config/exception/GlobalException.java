package com.example.api.config.exception;

public class GlobalException extends RuntimeException {

  private String errorCode;

  public GlobalException(String message) {
    super(message);
  }

  public GlobalException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
