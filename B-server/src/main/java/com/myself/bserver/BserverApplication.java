package com.myself.bserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangteng05
 * @description: springboot启动服务入口
 * @date 2021/5/18 12:27
 */
@SpringBootApplication
public class BserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(BserverApplication.class);
    }
}
