package com.demo.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @FileName ProductServerApplication
 * @Description
 * @Author WangTeng
 * @date 2026-03-23
 */
@SpringBootApplication
@MapperScan("com.demo.product.mapper")
public class ProductServerApplication {
    /**
     * @Description 应用程序入口
     * @Author Peter
     * @Date 2026-03-30
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductServerApplication.class, args);
    }
}
