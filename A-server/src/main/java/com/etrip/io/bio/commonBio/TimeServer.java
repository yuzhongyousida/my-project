package com.etrip.io.bio.commonBio;

import com.etrip.io.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: wangteng
 * @description: 同步阻塞I/O-服务端
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

            while (true){
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
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
