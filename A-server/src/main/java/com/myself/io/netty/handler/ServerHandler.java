package com.myself.io.netty.handler;

import com.myself.io.netty.codec.PacketCodec;
import com.myself.io.netty.packet.LoginRequestPacket;
import com.myself.io.netty.packet.LoginResponsePacket;
import com.myself.io.netty.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */

public class ServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 对象转换
        ByteBuf byteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.getInstance().decode(byteBuf);

        // 逻辑校验 + 封装响应包
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(packet.getVersion());
        if (packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            if (validLogin(loginRequestPacket)){
                System.out.println("登录校验成功");
                responsePacket.setSuccess(true);
            }else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("账号密码不匹配");
            }
        }else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("非登录请求");
        }


        // 编码
        ByteBuf out = PacketCodec.getInstance().encode(responsePacket);
        ctx.channel().writeAndFlush(out);

    }


    private boolean validLogin(LoginRequestPacket loginRequestPacket){
        if (loginRequestPacket==null){
            return false;
        }

        if (loginRequestPacket.getUserId()!=null && loginRequestPacket.getUserId()==1){
            return true;
        }

        return false;
    }
}
