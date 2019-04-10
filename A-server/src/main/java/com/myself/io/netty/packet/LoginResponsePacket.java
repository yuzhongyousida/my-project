package com.myself.io.netty.packet;

import lombok.Data;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */
@Data
public class LoginResponsePacket extends Packet{

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
