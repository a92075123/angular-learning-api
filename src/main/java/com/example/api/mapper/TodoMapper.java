package com.example.api.mapper;

import com.example.api.model.Todo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Todo Mapper 介面
 *
 * 使用 MyBatis 註解方式定義 SQL
 * 也可以使用 XML 方式（見 resources/mappers/TodoMapper.xml）
 */
@Mapper
public interface TodoMapper {

    /**
     * 查詢所有待辦事項
     */
    @Select("SELECT * FROM todos ORDER BY created_at ASC")
    List<Todo> findAll();

    /**
     * 根據完成狀態查詢
     */
    @Select("SELECT * FROM todos WHERE completed = #{completed} ORDER BY created_at DESC")
    List<Todo> findByCompleted(@Param("completed") Boolean completed);

    /**
     * 根據 ID 查詢
     */
    @Select("SELECT * FROM todos WHERE id = #{id}")
    List<Todo> findById(@Param("id") Long id);

    /**
     * 新增待辦事項
     */
    @Insert("INSERT INTO todos (todoTitle, todoContent, created_at, updated_at) " +
            "VALUES (#{todoTitle}, #{todoContent},NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Todo todo);

    /**
     * 更新待辦事項
     */
    @Update("UPDATE todos SET title = #{title}, completed = #{completed}, " +
            "priority = #{priority}, updated_at = NOW() WHERE id = #{id}")
    int update(Todo todo);

    /**
     * 切換完成狀態
     */
    @Update("UPDATE todos SET completed = NOT completed, updated_at = NOW() WHERE id = #{id}")
    int toggleCompleted(@Param("id") Long id);

    /**
     * 刪除待辦事項
     */
    @Delete("DELETE FROM todos WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    /**
     * 刪除所有已完成的待辦事項
     */
    @Delete("DELETE FROM todos WHERE completed = true")
    int deleteCompleted();

    /**
     * 統計數量
     */
    @Select("SELECT COUNT(*) FROM todos")
    int count();

    /**
     * 統計已完成數量
     */
    @Select("SELECT COUNT(*) FROM todos WHERE completed = true")
    int countCompleted();
}
