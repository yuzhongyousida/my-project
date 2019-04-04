package com.myself.web.controller;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/2.
 */
public class TestBioAndNio {


    public static void main(String[] args){

//        testInputStream();
//        testOutputStream();
//        testNIORead();
//        testNIOWrite();
        testNioClient();
        testNioServer();



    }

    /**
     * BIO之inputStream测试
     */
    public static void testInputStream(){
        InputStream inputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream("F:/a.txt"));
            byte[] buf = new byte[1024];
            int byteRead = inputStream.read(buf);
            while (byteRead != -1) {
                for (int i=0; i<byteRead; i++){
                    System.out.print((char) buf[i]);
                }
                byteRead = inputStream.read(buf);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * BIO之outputStream测试
     */
    public static void testOutputStream(){
        OutputStream outputStream = null;

        try {
            File file =  new File("F:/b.txt");
            if(!file.exists()){
                file.createNewFile();
            }

            outputStream = new BufferedOutputStream(new FileOutputStream(file));

            String content = "我是中国人！";
            byte[] buf = content.getBytes();

            outputStream.write(buf, 0, buf.length);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(outputStream!=null){
                    outputStream.flush();
                    outputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * NIO之读取测试（不引入selector）
     */
    public static void testNIORead(){
        RandomAccessFile aFile = null;//也可以用FileInputStream
        FileChannel fc = null;
        try {
            aFile = new RandomAccessFile("F:/a.txt","rw");
            fc = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int byteRead = fc.read(buffer);
            while (byteRead!=-1){
                buffer.flip();//调用flip()之后，读/写指针position指到缓冲区头部，并且设置了最多只能读出之前写入的数据长度(而不是整个缓存的容量大小)
                while (buffer.hasRemaining()){
                    System.out.print((char)buffer.get());
                }
                buffer.compact();
                byteRead = fc.read(buffer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(aFile!=null){
                    aFile.close();
                }
                if(fc!=null){
                    fc.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * NIO之写入测试（不引入selector）
     */
    public static void testNIOWrite(){
        RandomAccessFile aFile = null;//也可以用FileInputStream
        FileChannel fc = null;
        try {
            File file = new File("F:/b.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            aFile = new RandomAccessFile(file,"rw");
            fc = aFile.getChannel();
            fc.position(fc.size());

            String content = "abcdefg";
            ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
            fc.write(buffer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(aFile!=null){
                    aFile.close();
                }
                if(fc!=null){
                    fc.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * NIO之 SocketChannel-client
     */
    public static void testNioClient(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost",8088));

            int i=0;
            if(socketChannel.finishConnect()){
                while (true){
                    TimeUnit.SECONDS.sleep(i);
                    String info = "I'm "+i+++"-th information from client";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        System.out.println(buffer);
                        socketChannel.write(buffer);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(socketChannel!=null){
                    socketChannel.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * NIO之 SocketChannel-server
     */
    public static void testNioServer(){
        ServerSocket serverSocket = null;
        InputStream inputStream = null;

        try {
            serverSocket =  new ServerSocket(8088);
            int recvMsgSize  = 0;
            byte[] receiveBuffer = new byte[1024];
            while (true){
                Socket clientSocket = serverSocket.accept();
                SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
                System.out.println("Handling client at "+clientAddress);
                inputStream = clientSocket.getInputStream();
                while((recvMsgSize  = inputStream.read(receiveBuffer))!=-1){
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(receiveBuffer, 0, temp, 0, recvMsgSize);
                    System.out.println(new String(temp));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(serverSocket!=null){
                    serverSocket.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
