package com.etrip.io.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: wangteng
 * @description:
 * @date:2018/6/2
 */
public class TimeClientHandler extends ChannelHandlerAdapter{

    private int counter;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 因为LineBasedFrameDecoder是以换行符为结束标志的解码器，故后面一定要加上换行符；若读取的流最大长度超过了配置的单行最大长度时，还未读到换行符，则会跑出异常，同时忽略掉前面读取的异常流
        String requestStr = "what time is now ?" + System.getProperty("line.separator");
        byte[] requestBytes = requestStr.getBytes();
        ByteBuf msgBuf = null;
        for (int i=0; i<100; i++){
            try {
                msgBuf = Unpooled.buffer(requestBytes.length);
                msgBuf.writeBytes(requestBytes);
                ctx.writeAndFlush(msgBuf);
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            String responseStr = (String) msg;
            System.out.println(responseStr + ", the counter is : " + ++counter);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
