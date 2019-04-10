package com.myself.io.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 服务端读写处理类
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */

public class FirstServerHandler extends ChannelInboundHandlerAdapter{


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读取
        ByteBuf byteBuf = (ByteBuf) msg;
        String s = byteBuf.toString(Charset.forName("utf-8"));
        System.out.println(new Date() + "：服务端收到数据 ——> " + s);


        // 写入
        System.out.println(new Date() + "：服务端写出数据");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        // 1、获取二进制抽象ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();

        // 2、要写的数据转换为二进制数组
        byte[] data = "hello, 客户端".getBytes(Charset.forName("utf-8"));

        // 字节码填充进ByteBuf中
        byteBuf.writeBytes(data);

        return byteBuf;
    }



}
