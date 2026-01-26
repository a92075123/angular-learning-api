-- ============================================
-- Angular Learning API - 資料庫初始化腳本
-- ============================================

-- 建立資料庫（如果不存在）
-- CREATE DATABASE IF NOT EXISTS angular_learning
--     DEFAULT CHARACTER SET utf8mb4
--     DEFAULT COLLATE utf8mb4_unicode_ci;

-- USE angular_learning;

-- ============================================
-- 待辦事項表 (todos)
-- ============================================
CREATE TABLE IF NOT EXISTS todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主鍵 ID',
    title VARCHAR(200) NOT NULL COMMENT '標題',
    contant VARCHAR(200) NOT NULL COMMENT '內文',
    completed BOOLEAN DEFAULT FALSE COMMENT '是否完成',
    priority VARCHAR(20) DEFAULT 'medium' COMMENT '優先級: low, medium, high',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',

    INDEX idx_completed (completed),
    INDEX idx_priority (priority),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='待辦事項表';

-- ============================================
-- 使用者表 (users) - 進階練習用
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主鍵 ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '使用者名稱',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '電子郵件',
    password VARCHAR(255) NOT NULL COMMENT '密碼（加密後）',
    display_name VARCHAR(100) COMMENT '顯示名稱',
    avatar_url VARCHAR(500) COMMENT '頭像網址',
    role VARCHAR(20) DEFAULT 'user' COMMENT '角色: admin, editor, user',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否啟用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',

    INDEX idx_email (email),
    INDEX idx_username (username),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='使用者表';

-- ============================================
-- 插入測試資料
-- ============================================
INSERT INTO todos (title, completed, priority) VALUES
    ('學習 TypeScript 基礎', true, 'high'),
    ('完成 Angular 資料綁定練習', true, 'high'),
    ('練習 Angular 指令', false, 'medium'),
    ('建立 Todo App', false, 'high'),
    ('學習 Spring Boot', false, 'medium'),
    ('整合前後端', false, 'low')
ON DUPLICATE KEY UPDATE title = VALUES(title);
