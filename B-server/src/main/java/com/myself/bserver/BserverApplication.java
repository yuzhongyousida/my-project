package com.myself.bserver;

import com.myself.bserver.listener.MyApplicationStartedEventListener;
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
        SpringApplication app = new SpringApplication(BserverApplication.class);
        // 自定义listener
        app.addListeners(new MyApplicationStartedEventListener());
        app.run();
    }
}
