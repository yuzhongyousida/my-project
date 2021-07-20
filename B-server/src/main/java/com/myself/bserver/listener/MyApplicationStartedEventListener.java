package com.myself.bserver.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author wangteng05
 * @description: MyApplicationStartedEventListener类，ApplicationStartedEvent：springboot启动时执行的监听事件
 * @date 2021/7/19 17:17
 */
public class MyApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
    private static Logger LOGGER = LoggerFactory.getLogger(MyApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        LOGGER.info("============ MyApplicationStartedEventListener ============");

        SpringApplication app = applicationStartedEvent.getSpringApplication();
        // 设置Banner打印的一些属性
        app.setBannerMode(Banner.Mode.OFF);
        // 设置一个自定义的Banner（Banner的自定义可以直接在resource目录下建一个banner.txt文档，详见文档内容）
        app.setBanner(new MySpringBootBanner());
    }
}
