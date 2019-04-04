package com.myself.io.aio;

/**
 * @author: wangteng
 * @description:
 * @date:2018/5/30
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        try {
            new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
