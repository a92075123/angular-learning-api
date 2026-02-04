package com.example.api.controller;

import com.example.api.dto.TodoDto;
import com.example.api.model.ApiResponse;
import com.example.api.service.TodoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/todos")
public class TodoController {

  @Autowired
  private TodoService todoService;

  /**
   * 取得所有待辦事項
   */
  @GetMapping("/getAll")
  public ResponseEntity<?> getAll() {
    List<TodoDto> list = todoService.findAll();
    return ResponseEntity.ok(ApiResponse.success(list));
  }

  /**
   * 取得單一待辦事項
   */
  @GetMapping("/getOne/{title}")
  public ResponseEntity<?> getOne(@PathVariable(required = false) String title) {
    log.info("收到查詢請求: {}", title);
    List<TodoDto> todo = todoService.findByTitle(title);
    if (todo == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("待辦事項不存在"));
    }
    return ResponseEntity.ok(ApiResponse.success(todo));
  }

  /**
   * 新增待辦事項
   */
  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody TodoDto todo) {
    log.info("收到新增請求: {}", todo);
    todoService.create(todo);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("新增成功"));
  }

  /**
   * 更新待辦事項
   */
  @PostMapping("/update")
  public ResponseEntity<?> update(@RequestBody TodoDto todo) {
    log.info("收到新增請求: {}", todo);
    todoService.update(todo);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("更新成功"));
  }

  /**
   * 刪除待辦事項
   */
  @PostMapping("/delete")
  public ResponseEntity<?> delete(@RequestBody String id) {
    log.info("收到新增請求: {}", id);
    todoService.delete(Long.parseLong(id));
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("刪除成功"));
  }

  /**
   * 更新待辦事項項次
   */
  @PostMapping("/updateTodoLocation")
  public ResponseEntity<?> updateTodoLocation(@RequestBody List<TodoDto> req) {
    log.info("收到新增請求: {}", req);
    todoService.updateTodoLocation(req);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("更新成功"));
  }
}
