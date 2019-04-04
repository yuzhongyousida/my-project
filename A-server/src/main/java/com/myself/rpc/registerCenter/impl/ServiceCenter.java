package com.myself.rpc.registerCenter.impl;

import com.myself.rpc.registerCenter.Server;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/8/1.
 * Java实现RPC框架——注册中心接口实现类
 */
public class ServiceCenter implements Server {

    protected static final Logger logger = Logger.getLogger(ServiceCenter.class);

    /**
     * ThreadPoolExecutor的核心构造器的参数详解：
     * corePoolSize	核心线程池大小
     * maximumPoolSize	最大线程池大小
     * keepAliveTime	线程池中超过corePoolSize数目的空闲线程最大存活时间；可以allowCoreThreadTimeOut(true)使得核心线程有效时间
     * TimeUnit	keepAliveTime时间单位
     * workQueue	阻塞任务队列
     * threadFactory	新建线程工厂
     * RejectedExecutionHandler	当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
     */
    private static ExecutorService executor = new ThreadPoolExecutor(1,100,0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread();
        }
    });


    /**
     * 所有注册服务信息装载容器
     * key：service Interface name
     * value: serviceImpl Class
     */
    private static final HashMap<String,Class> serviceRegistry = new HashMap<String,Class>();

    private boolean isRunning = false;
    private int port = 6300;


    public ServiceCenter(int port) {
        this.port = port;
    }



    /**
     * 关闭注册中心
     */
    @Override
    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    /**
     * 注册中心启动
     * @throws IOException
     */
    @Override
    public void start() throws IOException {
        System.out.println("ServiceCenter starting...");
        try {
            // ServerSocket绑定端口进行监听
            ServerSocket server = new ServerSocket();
            server.bind(new InetSocketAddress(port));

            while(true){
                executor.execute(new ServiceTask(server.accept()));
                isRunning = true;
            }

        }catch (Exception e){
            logger.error("ServiceCenter start Exception : " + e.getStackTrace());
        }
    }

    @Override
    public void register(Class serviceInterface, Class impl) {
        if(serviceInterface==null || impl==null){
            logger.error("register service error, interface or impl is null ");
            return;
        }

        System.out.println("register service " + serviceInterface.getName());
        serviceRegistry.put(serviceInterface.getName(), impl);
    }

    @Override
    public Boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPort() {
        return port;
    }








    private static class ServiceTask implements Runnable{
        Socket client = null;

        public ServiceTask(Socket client){
            this.client = client;
        }


        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;

            try {
                // 将客户端发送的码流反序列化成对象，通过反射机制调用服务实现者，获取执行结果
                input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();//服务名
                String methodName = input.readUTF();//方法名
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();//参数类型数组
                Object[] arguments = (Object[]) input.readObject();//参数对象数组

                // 注册服务信息装载容器中取出注册服务类
                Class serviceClass = serviceRegistry.get(serviceName);
                if(serviceClass==null){
                    logger.error(serviceName+ " not found");
                    throw new ClassNotFoundException(serviceName+" not found");
                }

                // 通过反射获取到对应的方法实例,并执行该方法
                Method method = serviceClass.getMethod(methodName,parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(),arguments);

                // 将执行结果反序列化，通过socket发送给客户端
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(output!=null){
                    try {
                        output.flush();
                        output.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(input!=null){
                    try {
                        input.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(client!=null){
                    try {
                        client.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
