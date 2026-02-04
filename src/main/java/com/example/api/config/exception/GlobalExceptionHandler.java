package com.example.api.config.exception;

import com.example.api.model.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全域例外處理器
 * <p>
 * 統一處理 API 錯誤回應
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 處理驗證錯誤
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    log.warn("驗證錯誤: {}", errors);
    return ResponseEntity.badRequest()
        .body(ApiResponse.error("驗證失敗", errors));
  }

  /**
   * 處理一般執行時錯誤
   */
  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
    log.error("執行時錯誤: {}", ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error(ex.getMessage()));
  }

  /**
   * 處理所有其他錯誤
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
    log.error("系統錯誤: {}", ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error("系統錯誤，請稍後再試"));
  }
}
