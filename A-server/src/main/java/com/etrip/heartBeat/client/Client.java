package com.etrip.heartBeat.client;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/3
 */
public class Client {

    private static final Logger LOGGER = Logger.getLogger(Client.class);

    private String ip ;
    private int port;
    private String sendMsg;


    public Client(String ip, int port, String sendMsg) {
        this.ip = ip;
        this.port = port;
        this.sendMsg = sendMsg;
    }


    public void heartBeat(){
        OutputStream outputStream = null;
        BufferedReader reader = null;
        try {
            // 建立连接
            Socket client = new Socket(ip, port);
            System.out.println("连接已建立...");

            // 发送数据
            outputStream = client.getOutputStream();
            outputStream.write(sendMsg.getBytes("UTF-8"));
            outputStream.flush();

            // 接收数据
            InputStreamReader streamReader = new InputStreamReader(client.getInputStream());
            reader = new BufferedReader(streamReader);
            String receiveMsg = reader.readLine();

            System.out.println("接收到服务器的消息 ：" + receiveMsg);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.flush();
                    outputStream.close();
                }catch (Exception e){
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if(reader!=null){
                try {
                    reader.close();
                }catch (Exception e){
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            String sendMsg = "hello，服务端";
            Client client = new Client("127.0.0.1", 6300, sendMsg);
            client.heartBeat();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
