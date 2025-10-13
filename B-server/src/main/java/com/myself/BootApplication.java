package com.myself;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @FileName BootApplication
 * @Description
 * @Author bytedance
 * @date 2025-10-13
 */
@SpringBootApplication
@MapperScan("com.myself.mapper") // 扫描MyBatis的Mapper接口
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
