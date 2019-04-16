package com.myself.web.controller;

import com.myself.queue.LogHandler;

import java.util.Date;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/10
 */

public class TestQueue {
    private static LogHandler handler = new LogHandler();

    public static void main(String[] args) {
        // 先启动消费逻辑
        handler.consume();


        // 模拟不断新增日志记录
        Thread t = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Date d = new Date();
                        handler.addLog(d.toString());

                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();
    }



}
