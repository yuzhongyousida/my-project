package com.myself.io.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author: wangteng
 * @description:
 * @date:2018/5/30
 */
public class AsyncTimeServerHandler implements Runnable{

    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    private int port;


    public AsyncTimeServerHandler(int port){
        this.port = port;
        try {
            // 创建初始化一个异步服务端通道
            this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();

            // 绑定监听端口
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));

            System.out.println("the time server started at port : " + port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        // 初始化CountDownLatch对象是为了在一组正在执行的操作之前，允许当前线程一直阻塞，本例中让线程阻塞是为了防止服务端执行完成后退出，实际项目中不需要启动独立线程来处理asynchronousServerSocketChannel
        latch = new CountDownLatch(1);

        // 接收客户端连接（由于是异步操作，我们可以传递一个CompletionHandler<AsynchronousSocketChannel,? super A>
        // 类型的handler实例接收accept操作成功的通知消息，在本例程中我们通过AcceptCompletionHandler实例作为handler接收通知消息）
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());

        try {
            // 防止服务端执行完成后退出
            latch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
