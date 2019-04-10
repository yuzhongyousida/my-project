package com.myself.io.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 客户端读写处理类
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */

public class FirstClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端写数据");

        // 1、获取要写的数据二进制抽象ByteBuf对象
        ByteBuf byteBuf = getByteBuf(ctx);

        // 2、写数据
        ctx.channel().writeAndFlush(byteBuf);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + "：客户端收到数据 ——> " + byteBuf.toString(Charset.forName("utf-8")));
    }



    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        // 1、获取二进制抽象ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();

        // 2、要写的数据转换为二进制数组
        byte[] data = "hello, 服务端".getBytes(Charset.forName("utf-8"));

        // 字节码填充进ByteBuf中
        byteBuf.writeBytes(data);

        return byteBuf;
    }
}
