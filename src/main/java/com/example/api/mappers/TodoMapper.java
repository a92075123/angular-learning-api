package com.example.api.mappers;

import com.example.api.dto.TodoDto;
import com.example.api.generate.po.TodoEntity;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Todo Mapper 介面
 * <p>
 * 使用 MyBatis 註解方式定義 SQL
 * 也可以使用 XML 方式（見 resources/mappers/TodoMapper.xml）
 */
@Mapper
public interface TodoMapper {

  @Select("SELECT * FROM todos ORDER BY sort_no ASC ")
  List<TodoDto> findAll();

  @Select("SELECT * FROM todos WHERE todotitle = #{todotitle} ORDER BY sort_no ASC")
  List<TodoDto> findByTitle(@Param("todotitle") String todotitle);

  @Insert("INSERT INTO todos (todotitle, todocontent, created_at, updated_at, sort_no) " +
      "VALUES (#{todotitle}, #{todocontent},NOW(), NOW(),(SELECT COALESCE(MAX(sort_no), 0) + 1 FROM (SELECT sort_no FROM todos) AS temp))")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(TodoEntity todo);

  @Update("UPDATE todos SET todotitle = #{todotitle}, todocontent = #{todocontent}, updated_at = NOW() WHERE id = #{id}")
  int update(TodoEntity todo);

  void updateTodoLocation(@Param("list") List<TodoDto> todo);

  @Delete("DELETE FROM todos WHERE id = #{id}")
  int deleteById(@Param("id") Long id);

  @Delete("DELETE FROM todos")
  void deleteAllData();

}
