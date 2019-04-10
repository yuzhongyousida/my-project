package com.myself.io.netty;

import com.myself.io.netty.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */

public class NettyClient {

    static int MAX_RETRY_NUM = 3;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1、指定线程模型
                .group(workerGroup)
                // 2、指定IO模型
                .channel(NioSocketChannel.class)
                // 3、IO读写处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("客户端处理连接信息初始化");

                        // 指定连接数据读写逻辑处理
                        ch.pipeline().addLast(new ClientHandler());

                    }

                });

        // 连接
        connectRetry(bootstrap, "127.0.0.1", 1024, MAX_RETRY_NUM);

    }


    /**
     * 无最大重试次数的连接
     * @param bootstrap
     * @param host
     * @param port
     */
    private static void connect(Bootstrap bootstrap, String host, int port){
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()){
                        System.out.println("连接成功");
                    }else {
                        System.out.println("连接失败，重新连接");

                        connect(bootstrap, host, port+1);
                    }
                });
    }


    /**
     * 有最大重试次数限制的连接重试
     * @param bootstrap
     * @param host
     * @param port
     * @param retry 当前剩余的retry次数
     */
    private static void connectRetry(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()){
                        System.out.println("连接成功，" + host + ":" + port);
                    }else if (retry==0){
                        System.out.println("重试次数已用完，放弃连接！");
                    }else{
                        // 第几次重连
                        int order = MAX_RETRY_NUM - retry + 1;

                        // 本次重连的时间间隔
                        int delay = 1<< order;

                        System.out.println(new Date() + "：连接失败，第" + order + "次重新连接");

                        bootstrap.config()
                                .group()
                                .schedule(() -> connectRetry(bootstrap, host, port, retry-1), delay, TimeUnit.SECONDS);

                    }
                });
    }



}
