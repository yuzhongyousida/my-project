package com.myself.io.netty.codec;

import com.alibaba.fastjson.JSON;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/6
 */
public class JSONSerializer implements Serializer{

    @Override
    public byte getSerializeAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
