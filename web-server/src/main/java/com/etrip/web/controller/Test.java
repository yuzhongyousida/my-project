package com.etrip.web.controller;

import com.etrip.rpc.client.RPCClient;
import com.etrip.rpc.registerCenter.Server;
import com.etrip.rpc.registerCenter.impl.ServiceCenter;
import com.etrip.rpc.service.HelloService;
import com.etrip.rpc.service.impl.*;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2017/8/1.
 */
public class Test {




    public static void main(String[] args){
//        for (int i=0; i<10; i++){
//            System.out.println(Runtime.getRuntime().availableProcessors());
//        }
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
}
