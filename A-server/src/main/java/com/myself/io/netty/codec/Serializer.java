package com.myself.io.netty.codec;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */

public interface Serializer {

    /**
     * 默认序列化算法
     */
    Serializer DEFAULT = new JSONSerializer();


    /**
     * 序列化算法
     * @return
     */
    byte getSerializeAlgorithm();


    /**
     * 序列化
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deSerialize(Class<T> clazz, byte[] bytes);
}
