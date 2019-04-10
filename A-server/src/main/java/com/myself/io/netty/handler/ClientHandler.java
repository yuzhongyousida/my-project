package com.myself.io.netty.handler;

import com.myself.io.netty.codec.PacketCodec;
import com.myself.io.netty.packet.LoginRequestPacket;
import com.myself.io.netty.packet.LoginResponsePacket;
import com.myself.io.netty.packet.Packet;
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
        System.out.println(new Date() + "：客户端写数据");
        // 封装登录请求对象
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setPassword("123456");
        requestPacket.setUserName("wt");
        requestPacket.setUserId(1);

        // 编码
        ByteBuf byteBuf = PacketCodec.getInstance().encode(requestPacket);

        // 写数据
        ctx.channel().writeAndFlush(byteBuf);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.getInstance().decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }
    }
}
