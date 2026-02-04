package com.example.api.service;

import com.example.api.config.exception.GlobalException;
import com.example.api.config.exception.ResponseStatus;
import com.example.api.dto.TodoDto;
import com.example.api.generate.po.TodoEntity;
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
  public List<TodoDto> findAll() {
    return todoMapper.findAll();
  }

  /**
   * 根據 標題 查詢
   */
  public List<TodoDto> findByTitle(String title) {
    return todoMapper.findByTitle(title);
  }

  /**
   * 新增待辦事項
   */
  public void create(TodoDto todo) {
    TodoEntity entity = new TodoEntity();
    entity.setTodotitle(todo.getTodotitle());
    entity.setTodocontent(todo.getTodocontent());
    int count = todoMapper.insert(entity);
    if (count == 0) {
      throw new GlobalException(ResponseStatus.INSERT_ERROR.getMessage());
    }
  }

  /**
   * 更新待辦事項項次
   */
  public void updateTodoLocation(List<TodoDto> list) {
    todoMapper.updateTodoLocation(list);
  }

  /**
   * 刪除待辦事項
   */
  public void delete(Long id) {
    log.info("刪除待辦事項，id = {}", id);
    int rows = todoMapper.deleteById(id);
    if (rows == 0) {
      throw new GlobalException(ResponseStatus.DELETE_ERROR.getMessage());
    }
  }

  /**
   * 更新待辦事項
   */
  public void update(TodoDto todo) {
    TodoEntity entity = new TodoEntity();
    entity.setId(todo.getId());
    entity.setTodotitle(todo.getTodotitle());
    entity.setTodocontent(todo.getTodocontent());
    int count = todoMapper.update(entity);
    if (count == 0) {
      throw new GlobalException(ResponseStatus.UPDATE_ERROR.getMessage());
    }
  }
}

