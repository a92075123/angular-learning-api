package com.example.api.service;

import com.example.api.generate.po.Todo;
import com.example.api.mappers.TodoMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Todo 服務層
 * <p>
 * 處理業務邏輯，調用 Mapper 進行資料庫操作
 */
@Slf4j
@Service
@Transactional
public class TodoService {


  @Autowired
  private TodoMapper todoMapper;

  /**
   * 取得所有待辦事項
   */
  public List<Todo> findAll() {
    return todoMapper.findAll();
  }

  /**
   * 根據 標題 查詢
   */
  public List<Todo> findByTitle(String title) {
    log.debug("查詢待辦事項，title = {}", title);
    return todoMapper.findByTitle(title);
  }

  /**
   * 新增待辦事項
   */
  public Todo create(Todo todo) {
    log.info("新增待辦事項: {}", todo.getTodotitle());
    todoMapper.insert(todo);
    return todo;
  }

  /**
   * 更新待辦事項項次
   */
  public void updateTodoLocation(List<Todo> list) {
    todoMapper.updateTodoLocation(list);
  }

  /**
   * 刪除待辦事項
   */
  public void delete(Long id) {
    log.info("刪除待辦事項，id = {}", id);
    int rows = todoMapper.deleteById(id);
    if (rows == 0) {
      throw new RuntimeException("待辦事項不存在: " + id);
    }
  }

  /**
   * 更新待辦事項
   */
  public void update(Todo todo) {
    todoMapper.update(todo);
  }
}

