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
        testMappedByteBuffer();

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
     * 用 ByteBuffer读取大文件测试,与下面的的MappedByteBuffer速度进行比较
     */
    private static void testByteBuffer(){
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            long startTime = System.currentTimeMillis();

            aFile = new RandomAccessFile("F:/test.pdf","rw");
            fc = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int)aFile.length());
            buffer.clear();
            fc.read(buffer);
            System.out.println((char) buffer.get((int) (aFile.length() / 2 - 1)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)+1));

            long endTime = System.currentTimeMillis();

            System.out.println("total time is " + (endTime-startTime) + " ms");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 用 MappedByteBuffer读取大文件测试,与上面的的ByteBuffer速度进行比较
     */
    private static void testMappedByteBuffer(){
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            long startTime = System.currentTimeMillis();

            aFile = new RandomAccessFile("F:/test.pdf","rw");
            fc = aFile.getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY,0,aFile.length());
            System.out.println((char)mbb.get((int)(aFile.length()/2-1)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)+1));

            long endTime = System.currentTimeMillis();

            System.out.println("total time is " + (endTime-startTime) + " ms");
        }catch (Exception e){
            e.printStackTrace();
        }

    }


//    private void testCpu(){
//        try {
//            Sigar sigar = new Sigar();
//            CpuPerc cpuPerc = sigar.getCpuPerc();
//            System.out.println("当前cpu的空闲率: " + CpuPerc.format(cpuPerc.getIdle()));
//            System.out.println("当前cpu的占用率： "+ CpuPerc.format(cpuPerc.getCombined()));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }












}
