package com.myself.io.netty1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: wangteng
 * @description:
 * @date:2018/6/2
 */
public class TimeServerHandler extends ChannelHandlerAdapter{

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // 读
            ByteBuf readBuffer = (ByteBuf) msg;
            byte[] bytes = new byte[readBuffer.readableBytes()];
            readBuffer.readBytes(bytes);
            String requestStr = new String(bytes, "UTF-8");

            System.out.println("the server received msg is : " + requestStr + ", the counter is : " + ++counter);

            // 写
            String responseStr = "now time is : " + System.currentTimeMillis();
            ByteBuf writeBuffer = Unpooled.copiedBuffer(responseStr.getBytes());
            ctx.writeAndFlush(writeBuffer);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
