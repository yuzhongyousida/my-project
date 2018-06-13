package com.etrip.io.aio;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author: wangteng
 * @description:
 * @date:2018/5/30
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer>{

    private AsynchronousSocketChannel channel;

    /**
     * 将AsynchronousSocketChannel通过参数传递到ReadCompletionHandler中当作成员变量来使用，主要用于读取半包消息和发送应答
     * @param channel
     */
    public ReadCompletionHandler(AsynchronousSocketChannel channel){
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        try {
            // 读取
            attachment.flip();
            byte[] bytes = new byte[attachment.remaining()];
            attachment.get(bytes);
            String requestStr = new String(bytes, "UTF-8");
            System.out.println("timer server receive msg is : " + requestStr);

            // 响应
            doWrite("now is : " + System.currentTimeMillis());


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void doWrite(String responseStr){
        if (!StringUtils.isEmpty(responseStr)){
            byte[] bytes = responseStr.getBytes();
            final ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();

            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    // 没有发完继续发送
                    if (attachment.hasRemaining()){
                        /** 异步写消息的参数解析：
                         *  1)ByteBuffer src：写入缓冲区，用于异步向Channel中写入数据包；
                         *  2)A attachment：异步Channel携带的附件，通知回调的时候作为入参使用；
                         *  3)CompletionHandler<Integer,? super A> handler：写入完毕之后通知回调的业务handler，此处是一个递归调用
                         */
                        channel.write(writeBuffer, writeBuffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
