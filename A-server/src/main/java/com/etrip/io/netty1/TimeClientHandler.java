package com.etrip.io.netty1;

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
        // 写
        String requestStr = "what time is now ?";
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
            // 读
            ByteBuf readBuffer = (ByteBuf) msg;
            byte[] bytes = new byte[readBuffer.readableBytes()];
            readBuffer.readBytes(bytes);
            String responseStr = new String(bytes, "UTF-8");

            System.out.println("the client receive msg is : " + responseStr + ", the counter is : " + ++counter);
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
