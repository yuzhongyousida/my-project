package com.etrip.io.bio.ThreadPoolBio;

import com.etrip.io.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: wangteng
 * @description: 采用线程池和队列实现一个伪异步I/O模型——服务端
 * @date:2018/5/29
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;

        ServerSocket server = null;
        try {

            server = new ServerSocket(port);

            System.out.println("the time server is start in port : " + port);

            Socket socket = null;
            TimerServerHandlerExecutePool threadPool = new TimerServerHandlerExecutePool(50,10000);

            while (true){
                socket = server.accept();
                threadPool.execute(new Thread(new TimeServerHandler(socket)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (server!=null){
                server.close();
                server = null;
            }
        }
    }
}
