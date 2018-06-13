package com.etrip.io.netty1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: wangteng
 * @description: netty 不使用任何解码器（解决不了tcp拆包/粘包问题）的客户端
 * @date:2018/6/2
 */
public class TimeClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8080;
        try {
            new TimeClient().connect(host, port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void connect(String host, int port) throws InterruptedException {
        // nio线程组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 启动辅助类初始化及配置
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientChildChannelHandler());

            // 同步阻塞等待连接成功
            ChannelFuture f = bootstrap.connect(host, port).sync();

            System.out.println("the client connected succeed at : " + host + ":" + port);

            // 同步阻塞等待客户端链路关闭
            f.channel().closeFuture().sync();

        }finally {
            // 优雅退出，释放线程组资源
            group.shutdownGracefully();
        }
    }




    private class ClientChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            try {
                socketChannel.pipeline().addLast(new TimeClientHandler());
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }


}
