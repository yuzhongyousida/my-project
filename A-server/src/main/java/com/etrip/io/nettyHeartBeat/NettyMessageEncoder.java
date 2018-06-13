package com.etrip.io.nettyHeartBeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author: wangteng
 * @description: NettyMessage编码器
 * @date:2018/6/10
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage>{


    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {

    }
}
