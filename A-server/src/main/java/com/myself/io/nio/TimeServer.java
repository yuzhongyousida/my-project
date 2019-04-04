package com.myself.io.nio;

/**
 * @author: wangteng
 * @description: NIO(同步非阻塞I/O)——服务端
 * @date:2018/5/30
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        try {
            MultiplexerTimerServer server = new MultiplexerTimerServer(port);

            new Thread(server,"NIO-MultiplexerTimerServer-001").start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
