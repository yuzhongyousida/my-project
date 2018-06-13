package com.etrip.io.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author: wangteng
 * @description: webSocket服务端
 * @date:2018/6/10
 */
public class WebSocketServer {

    public static void main(String[] args) {
        int port = 8080;
        try {
            new WebSocketServer().run(port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void run(int port) throws InterruptedException {
        // 服务端NIO线程组（acceptGroup用于服务端接受客户端连接， wordGroup用于处理SocketChannel的网络读写）
        EventLoopGroup acceptGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // NIO服务端的辅助启动类ServerBootstrap实例生成
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 用NIO线程组及生成的NioSocketSocketChannel实例配置辅助启动类，并配置NioSocketSocketChannel实例的tcp参数等属性，以及I/O事件处理handler
            serverBootstrap.group(acceptGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChildChannelHandler());

            // 绑定端口，调用同步阻塞方法sync()，等待绑定操作完成，返回的是用于异步操作的通知回调ChannelFuture实例
            ChannelFuture f = serverBootstrap.bind(port).sync();

            System.out.println("the webSocket server started at port : " + port);

            // 等待服务器监听端口关闭
            f.channel().closeFuture().sync();

        }finally {
            // 优雅退出，释放线程池资源
            acceptGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }



    private class ServerChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            try {
                // HttpServerCodec：将请求或应答详细编码或解码成HTTP消息
                socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());

                // HttpObjectAggregator：将HTTP消息的多个部分合并成一个完整的HTTP消息
                socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));

                // 向客户端发送HTML5文件，主要用来支持服务端和浏览器端进行WebSocket通信
                socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());

                // 自定义的webSocket请求处理类
                socketChannel.pipeline().addLast("web-socket-handler", new WebSocketServerHandler());
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }

}
