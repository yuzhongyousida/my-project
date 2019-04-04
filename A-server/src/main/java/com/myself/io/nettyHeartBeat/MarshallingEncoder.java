package com.myself.io.nettyHeartBeat;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author: wangteng
 * @description:
 * @date:2018/6/10
 */
public class MarshallingEncoder {

    /**
     * 占位符（4个字节）
     */
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];


    Marshaller marshaller;

    public MarshallingEncoder(MarshallerProvider provider) throws IOException {
        marshaller = MarshallingCodeCFactory.buildMarshalling();
    }

    /**
     * jboss的marshalling编码方法
     */
    protected void encode(Object msg, ByteBuf out){
        try {
            int lengthPos = out.writerIndex();

            out.writeBytes(LENGTH_PLACEHOLDER);

            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();

            out.setInt(lengthPos, out.writerIndex() - lengthPos -4);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                marshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

}
