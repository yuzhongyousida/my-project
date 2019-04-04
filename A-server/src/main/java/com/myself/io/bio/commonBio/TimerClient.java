package com.myself.io.bio.commonBio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author: wangteng
 * @description: 同步阻塞I/O-客户端
 * @date:2018/5/29
 */
public class TimerClient {

    public static void main(String[] args) {
        int port = 8080;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("what time is now ?");

            System.out.println("the time client send msg succeed");

            String responsStr = in.readLine();

            System.out.println("the time server response --- " + responsStr);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (out!=null){
                out.close();
                out = null;
            }
            if (in!=null){
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
