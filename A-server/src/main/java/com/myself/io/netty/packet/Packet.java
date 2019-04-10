package com.myself.io.netty.packet;

import lombok.Data;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */
@Data
public abstract class Packet {

    /**
     *  协议格式
     *
     * * +-------------------+--------------------+-------------------+----------+--------------+---------------+
     * * + magicCode(4Byte)  |       1Byte        |       1Byte       |   1Byte  |    4Byte     |     n Byte    |
     * * +-------------------+--------------------+-------------------+----------+--------------+---------------+
     * * +     0x12345678    |      version       |   序列化算法类型    |   指令   |    数据长度   |       数据     |
     * * +-------------------+--------------------+-------------------+----------+--------------+---------------+
     * *
     */



    /**
     * 协议版本
     */
    public Byte version = 1;


    /**
     * 指令获取
     * @return
     */
    public abstract Byte getCommand();



}
