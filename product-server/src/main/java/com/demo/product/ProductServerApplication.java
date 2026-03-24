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
    public static void main(String[] args) {
        SpringApplication.run(ProductServerApplication.class, args);
    }
}
