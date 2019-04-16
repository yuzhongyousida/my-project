package com.myself.io.netty.handler;

import com.myself.io.netty.codec.PacketCodec;
import com.myself.io.netty.packet.LoginRequestPacket;
import com.myself.io.netty.packet.LoginResponsePacket;
import com.myself.io.netty.packet.MessageResponsePacket;
import com.myself.io.netty.packet.Packet;
import com.myself.io.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */

public class ClientHandler extends ChannelInboundHandlerAdapter{


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = null;
        try {
            System.out.println(new Date() + "：客户端写数据");
            // 封装登录请求对象
            LoginRequestPacket requestPacket = new LoginRequestPacket();
            requestPacket.setPassword("123456");
            requestPacket.setUserName("wt");
            requestPacket.setUserId(1);

            // 编码
            byteBuf = PacketCodec.getInstance().encode(requestPacket);

            // 写数据
            ctx.channel().writeAndFlush(byteBuf);

        }catch (Exception e){
            e.printStackTrace();
        } finally{
            // 内存释放
            if (byteBuf!=null){
                byteBuf.release();
            }
        }

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        try {
            Packet packet = PacketCodec.getInstance().decode(byteBuf);

            if (packet instanceof LoginResponsePacket) {
                LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

                if (loginResponsePacket.isSuccess()) {
                    // 设置登录成功标志
                    LoginUtil.markLogin(ctx.channel());

                    System.out.println(new Date() + ": 客户端登录成功");
                } else {
                    System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
                }
            }else if (packet instanceof MessageResponsePacket){
                MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
                System.out.println(messageResponsePacket.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            // 内存释放
            if (byteBuf!=null){
                byteBuf.release();
            }
        }
    }
}
