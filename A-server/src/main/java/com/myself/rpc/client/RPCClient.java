package com.myself.rpc.client;

import org.apache.log4j.Logger;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2017/8/1.
 * 利用动态代理获取rpc接口的实例对象
 */
public class RPCClient<T> {

    protected static final Logger logger = Logger.getLogger(RPCClient.class);

    public static<T> T getRemoteProxyObj(final Class<T> serviceInterface, final InetSocketAddress addr){
        if(serviceInterface==null || addr==null){
            logger.error("parameter is null");
            return null;
        }


        // 1、将本地的接口调用转换成JDK的动态代理，在动态代理中实现接口的远程调用
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = null;
                        ObjectInputStream inputStream = null;
                        ObjectOutputStream outputStream = null;
                        try {
                            //2、创建socket客户端，根据指定的地址连接远程服务提供者
                            socket = new Socket();
                            socket.connect(addr);

                            //3、将远程服务调用所需的接口类、方法名、参数列表等编码后发送给服务提供者
                            outputStream = new ObjectOutputStream(socket.getOutputStream());
                            outputStream.writeUTF(serviceInterface.getName());
                            outputStream.writeUTF(method.getName());
                            outputStream.writeObject(method.getParameterTypes());
                            outputStream.writeObject(args);

                            //4、同步阻塞等待服务器返回响应，获取应答后返回
                            inputStream = new ObjectInputStream(socket.getInputStream());

                            return inputStream.readObject();
                        }finally {
                            if(socket!=null){
                                socket.close();
                            }
                            if(outputStream!=null){
                                outputStream.flush();
                                outputStream.close();
                            }
                            if(inputStream!=null){
                                inputStream.close();
                            }
                        }
                    }
                });
    }


}
