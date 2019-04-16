package com.myself.io.netty.packet;

/**
 * 消息类型
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */
public interface Command {

    byte LOGIN_REQUEST = 1;

    byte LOGIN_RESPONSE = 2;

    byte MESSAGE_REQUEST = 3;

    byte MESSAGE_RESPONSE = 4;


}
