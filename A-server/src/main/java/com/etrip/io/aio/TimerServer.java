package com.etrip.io.aio;

/**
 * @author: wangteng
 * @description:
 * @date:2018/5/30
 */
public class TimerServer {

    public static void main(String[] args) {
        int port = 8080;
        try {
            AsyncTimeServerHandler asyncTimeServerHandler = new AsyncTimeServerHandler(port);

            new Thread(asyncTimeServerHandler,"AIO-AsyncTimeServerHandler-001").start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
