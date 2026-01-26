package com.example.api.service;

import com.example.api.mapper.TodoMapper;
import com.example.api.model.Todo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Todo 服務層
 *
 * 處理業務邏輯，調用 Mapper 進行資料庫操作
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoMapper todoMapper;

    /**
     * 取得所有待辦事項
     */
    public List<Todo> findAll() {
        return todoMapper.findAll();
    }

    /**
     * 根據完成狀態篩選
     */
    public List<Todo> findByCompleted(Boolean completed) {
        log.debug("查詢待辦事項，completed = {}", completed);
        return todoMapper.findByCompleted(completed);
    }

    /**
     * 根據 ID 查詢
     */
    public Todo findById(Long id) {
        log.debug("查詢待辦事項，id = {}", id);
        return todoMapper.findById(id);
    }

    /**
     * 新增待辦事項
     */
   
    public Todo create(Todo todo) {
        log.info("新增待辦事項: {}", todo.getTodoTitle());
        todoMapper.insert(todo);
        return todo;
    }

    /**
     * 更新待辦事項
     */
   
    public Todo update(Long id, Todo todo) {
        log.info("更新待辦事項，id = {}", id);

        Todo existing = todoMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("待辦事項不存在: " + id);
        }

        todoMapper.update(todo);
        return todoMapper.findById(id);
    }

    /**
     * 切換完成狀態
     */
   
    public Todo toggleCompleted(Long id) {
        log.info("切換完成狀態，id = {}", id);

        Todo existing = todoMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("待辦事項不存在: " + id);
        }

        todoMapper.toggleCompleted(id);
        return todoMapper.findById(id);
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
     * 刪除所有已完成的待辦事項
     */
   
    public int deleteCompleted() {
        log.info("刪除所有已完成的待辦事項");
        return todoMapper.deleteCompleted();
    }

    /**
     * 取得統計資訊
     */
    public Map<String, Integer> getStats() {
        int total = todoMapper.count();
        int completed = todoMapper.countCompleted();
        int active = total - completed;

        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("completed", completed);
        stats.put("active", active);

        return stats;
    }
}
