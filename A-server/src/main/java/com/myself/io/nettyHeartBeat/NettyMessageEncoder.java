package com.myself.io.nettyHeartBeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.Map;

/**
 * @author: wangteng
 * @description: NettyMessage编码器
 * @date:2018/6/10
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage>{

    private MarshallingEncoder marshallingEncoder;

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        if (msg==null || msg.getHeader()==null){
            throw new Exception("the encode message is null");
        }

        ByteBuf  sendBuf = Unpooled.buffer();
        Header header = msg.getHeader();
        // netty消息的校验码
        sendBuf.writeInt(header.getCrcCode());
        // 信息头长度
        sendBuf.writeInt(header.getLength());

        // 会话ID
        sendBuf.writeLong(header.getSessionId());

        // 消息类型
        sendBuf.writeByte(header.getType());

        // 消息优先级
        sendBuf.writeByte(header.getPriority());

        // 扩展消息个数
        sendBuf.writeInt(header.getAttachment().size());

        // 可扩展消息内容
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> entry : header.getAttachment().entrySet()){
            key = entry.getKey();
            keyArray = key.getBytes("UTF-8");
            // 扩展消息的key值字节长度
            sendBuf.writeInt(keyArray.length);

            // 扩展消息的key值字节数组
            sendBuf.writeBytes(keyArray);

            // 利用marshalling将扩展消息的value序列化成byte数组，然后写入ByteBuf缓冲区
            marshallingEncoder.encode(entry.getValue(), sendBuf);
        }

        // body部分也利用marshalling序列化成byte数组，写入ByteBuf缓冲区
        if (msg.getBody() != null) {
            marshallingEncoder.encode(msg.getBody(), sendBuf);
        } else{
            sendBuf.writeInt(0);
        }

        key = null;
        keyArray = null;
        value = null;
    }
}
