package com.myself.io.netty.packet;

import lombok.Data;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/10
 */
@Data
public class MessageRequestPacket extends Packet{

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
