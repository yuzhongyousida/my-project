package com.myself.io.netty.packet;

import lombok.Data;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */
@Data
public class LoginRequestPacket extends Packet{

    private Integer userId;

    private String userName;

    private String password;



    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }


}
