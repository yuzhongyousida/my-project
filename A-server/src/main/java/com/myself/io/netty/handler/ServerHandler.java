package com.myself.io.netty.handler;

import com.myself.io.netty.codec.PacketCodec;
import com.myself.io.netty.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

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
        Packet response = null;

        // 逻辑校验 + 封装响应包
        if (packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());
            if (validLogin(loginRequestPacket)){
                System.out.println("登录校验成功");
                responsePacket.setSuccess(true);
            }else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("账号密码不匹配");
            }
            response = responsePacket;
        }else if (packet instanceof MessageRequestPacket){
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            String message = requestPacket.getMessage();
            System.out.println(new Date() + "，服务端接收到客户端消息：" + message);

            // 响应消息 todo 可以同样改为控制台输入进行响应
            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setMessage("服务端报时：" + new Date());
            response = responsePacket;
        }


        // 编码
        ByteBuf out = PacketCodec.getInstance().encode(response);
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
