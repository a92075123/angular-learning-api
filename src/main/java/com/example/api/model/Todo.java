package com.example.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 待辦事項實體類別
 *
 * 使用 Lombok 自動產生 getter/setter/constructor 等
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {


    private Integer id;

    /**
     * 待辦事項標題
     */
    @NotBlank(message = "標題不能為空")
    @Size(min = 1, max = 200, message = "標題長度必須在 1-200 字之間")
    private String todoTitle;

    /**
     * 待辦事項內文
     */
    private String todoContent;

    /**
     * 創建日期
     */
    private Timestamp created_at;


    /**
     * 更新時間
     */
    private Timestamp updated_at;

    /**
     * 資料順序
     */
    private Integer sort_no;


}
