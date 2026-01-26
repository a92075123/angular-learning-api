package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 應用程式入口
 *
 * @SpringBootApplication 包含：
 * - @Configuration: 標記為配置類
 * - @EnableAutoConfiguration: 啟用自動配置
 * - @ComponentScan: 掃描元件
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
