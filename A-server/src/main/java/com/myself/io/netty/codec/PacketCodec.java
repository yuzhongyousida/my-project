package com.myself.io.netty.codec;

import com.myself.io.netty.packet.Command;
import com.myself.io.netty.packet.LoginRequestPacket;
import com.myself.io.netty.packet.LoginResponsePacket;
import com.myself.io.netty.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */

public class PacketCodec {

    private static final int MAGIC_NUMBER = 0x12345678;

    private static PacketCodec instance;

    public static PacketCodec getInstance(){
        if (null==instance){
            synchronized (PacketCodec.class){
                if (null==instance){
                    instance = new PacketCodec();
                }
            }
        }

        return instance;
    }

    public ByteBuf encode(Packet packet){
        // ByteBuf对象生成
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();

        // 序列化
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 协议包编码逻辑
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }




    public Packet decode(ByteBuf byteBuf){
        // 跳过magicNumber
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        // 数据包
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        // 反序列化
        Class<? extends Packet> packetClazz = getPacketClassType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (packetClazz!=null && serializer!=null){
            return serializer.deSerialize(packetClazz, bytes);
        }

        return null;
    }




    /**
     * 根据命令类型，获取请求包class
     * @param command
     * @return
     */
    private Class<? extends Packet> getPacketClassType(byte command){
        switch (command){
            case Command.LOGIN_REQUEST :
                return LoginRequestPacket.class;
            case Command.LOGIN_RESPONSE :
                return LoginResponsePacket.class;

            default: return null;
        }
    }

    /**
     * 获取对应的解析类
     * @param serializeAlgorithm
     * @return
     */
    private Serializer getSerializer(byte serializeAlgorithm){
        switch (serializeAlgorithm){
            case SerializerAlgorithm.JSON :
                return new JSONSerializer();

            default: return null;
        }
    }
}
