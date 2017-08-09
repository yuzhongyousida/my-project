package com.etrip.util.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/18.
 */
public class Test {


    public static void main(String[] args) {
        try {


            // 服务端
            ServerSocket ss = new ServerSocket(10086);
            Socket socket = ss.accept();
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[200];
            int length = 0;
            while (-1 != (length = is.read(buffer, 0, buffer.length)))//这句错了，是不等于！
            {
                String str = new String(buffer, 0, length);
                System.out.println("welcome "+str);
            }

            // 客户端
            Socket s =new Socket("127.0.0.1",10086);
            OutputStream os = s.getOutputStream();
            os.write("hello world".getBytes());
            os.close();//客户端使用完流之后记得要关闭！！


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
