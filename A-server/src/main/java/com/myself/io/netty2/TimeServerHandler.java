package com.myself.io.netty2;

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
            String requestStr = (String) msg;
            System.out.println(requestStr + ", the counter is : " + ++counter);

            // 写 (因为LineBasedFrameDecoder是以换行符为结束标志的解码器，故后面一定要加上换行符；若读取的流最大长度超过了配置的单行最大长度时，还未读到换行符，则会跑出异常，同时忽略掉前面读取的异常流)
            String responseStr = "now time is : " + System.currentTimeMillis() + System.getProperty("line.separator");
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
