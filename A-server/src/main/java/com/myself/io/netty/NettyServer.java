package com.myself.io.netty;

import com.myself.io.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */

public class NettyServer {

    public static void main(String[] args) {
        // 对外接活（处理连接）的老板：accept 新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 对每单活（每个连接）处理的工人：处理每一条连接的数据读写的线程组
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 启动一个netty服务端
        serverBootstrap
                // 1、指定线程模型
                .group(bossGroup, workGroup)
                // 2、指定IO模型
                .channel(NioServerSocketChannel.class)
                // 3、连接读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });


        // 端口绑定
        bind(serverBootstrap, 1000);
    }


    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(future -> {
           if (future.isSuccess()){
               System.out.println("端口["+ port +"]绑定成功");
           }else {
               System.err.println("端口[" + port + "]绑定失败");
               bind(serverBootstrap, port+1);
           }
        });
    }

}
