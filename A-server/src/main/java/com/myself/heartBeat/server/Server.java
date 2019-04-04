package com.myself.heartBeat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/2
 */
public class Server implements Runnable{
    protected static final Logger LOGGER = Logger.getLogger(Server.class);

    private int port = 6300;
    private ServerSocket serverSocket;


    public Server() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("server starting...");
    }


    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            while (true){
                Socket socket = serverSocket.accept();

                // 读取client端发送的数据
                inputStream = socket.getInputStream();
                byte[] bytes = new byte[2048];
                int len;
                StringBuilder sb = new StringBuilder();
                while ((len = inputStream.read(bytes)) != -1) {
                    sb.append(new String(bytes, 0, len,"UTF-8"));
                }
                System.out.println("服务端接收到信息：" + sb.toString());

                // 发送信息
                String sendMsg = "hello, I'm server";
                outputStream = socket.getOutputStream();
                outputStream.write(sendMsg.getBytes("UTF-8"));
                outputStream.flush();
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if(outputStream != null){
                try {
                    outputStream.flush();
                    outputStream.close();
                }catch (Exception e){
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }



    public static void main(String[] args) {
        try {
            // 启动服务端
            Thread thread = new Thread(new Server());
            thread.start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
