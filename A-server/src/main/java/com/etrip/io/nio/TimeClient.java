package com.etrip.io.nio;

/**
 * @author: wangteng
 * @description: NIO(同步非阻塞I/O)——客户端
 * @date:2018/5/30
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        try {
            TimeClientHandler timeClientHandler = new TimeClientHandler("127.0.0.1", port);
            new Thread(timeClientHandler, "NIO-TimeClientHandler-001").start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
