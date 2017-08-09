package com.etrip.web.controller;

import com.etrip.rpc.client.RPCClient;
import com.etrip.rpc.registerCenter.Server;
import com.etrip.rpc.registerCenter.impl.ServiceCenter;
import com.etrip.rpc.service.HelloService;
import com.etrip.rpc.service.impl.*;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/8/1.
 */
public class Test {




    public static void main(String[] args){
//        test1();
//        testRpc();

        testByteBuffer();
//        testMappedByteBuffer();

    }


    /**
     * 获取可用的java虚拟机的处理器个数
     */
    private static void test1(){
        for (int i=0; i<10; i++){
            System.out.println(Runtime.getRuntime().availableProcessors());
        }
    }


    /**
     * 测试Java实现RPC框架
     */
    private static void testRpc(){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Server serviceServer = new ServiceCenter(8088);
                        serviceServer.register(HelloService.class, HelloServiceImpl.class);
                        serviceServer.start();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();

            HelloService service = RPCClient.getRemoteProxyObj(HelloService.class,new InetSocketAddress("localhost",8088));

            System.out.println(service.sayHi("wangteng"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 用 ByteBuffer读取大文件测试
     */
    private static void testByteBuffer(){
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            long startTime = System.currentTimeMillis();

            aFile = new RandomAccessFile("F:/test.pdf","rw");
            aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int)aFile.length());
            buffer.clear();
            fc.read(buffer);

            long endTime = System.currentTimeMillis();

            System.out.println("total time is " + (endTime-startTime) + " ms");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 用 MappedByteBuffer读取大文件测试
     */
    private static void testMappedByteBuffer(){
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            long startTime = System.currentTimeMillis();

            aFile = new RandomAccessFile("F:/test.pdf","rw");
            aFile.getChannel();
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY,0,aFile.length());
            buffer.clear();
            fc.read(buffer);

            long endTime = System.currentTimeMillis();

            System.out.println("total time is " + (endTime-startTime) + " ms");
        }catch (Exception e){
            e.printStackTrace();
        }
    }














}
