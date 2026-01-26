package com.example.api.controller;

import com.example.api.model.ApiResponse;
import com.example.api.model.Todo;
import com.example.api.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Todo API 控制器
 *
 * RESTful API 端點：
 * GET    /api/todos          - 取得所有待辦事項
 * GET    /api/todos/{id}     - 取得單一待辦事項
 * POST   /api/todos          - 新增待辦事項
 * PUT    /api/todos/{id}     - 更新待辦事項
 * PATCH  /api/todos/{id}/toggle - 切換完成狀態
 * DELETE /api/todos/{id}     - 刪除待辦事項
 * DELETE /api/todos/completed - 刪除已完成項目
 * GET    /api/todos/stats    - 取得統計資訊
 */
@Slf4j
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 取得所有待辦事項
     *
     */
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Todo> list = todoService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * 取得單一待辦事項
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Todo>> getById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);

        if (todo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("待辦事項不存在"));
        }

        return ResponseEntity.ok(ApiResponse.success(todo));
    }

    /**
     * 新增待辦事項
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Todo todo) {
        log.info("收到新增請求: {}", todo);

        todoService.create(todo);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


}
