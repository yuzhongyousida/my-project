package com.myself.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author: wangteng
 * @description:
 * @date:2018/5/30
 */
public class AcceptCompletionHandler implements
        CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler>{


    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {

        /** 既然已经接收客户端成功了，为什么还要再次调用accept方法呢？
         *  原因是这样的：当我们调用AsynchronousServerSocketChannel的accept方法后，
         *  如果有新的客户端连接接入，系统将回调我们传入的CompletionHandler实例的completed方法，
         *  表示新的客户端已经接入成功，因为一个AsynchronousServerSocketChannel可以接收成千上万个客户端，
         *  所以我们需要继续调用它的accept方法，接收其它的客户端连接，最终形成一个循环。
         *  每当接收一个客户读连接成功之后，再异步接收新的客户端连接。
         */
        attachment.asynchronousServerSocketChannel.accept(attachment, this);

        // 异步读取请求消息
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        /** 异步读取消息的参数解析：
         *  1)ByteBuffer dst：接收缓冲区，用于从异步Channel中读取数据包；
         *  2)A attachment：异步Channel携带的附件，通知回调的时候作为入参使用；
         *  3)CompletionHandler<Integer,? super A> handler：接收读取完毕之后通知回调的业务handler，本例程中为ReadCompletionHandler。
         */
        result.read(buffer, buffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
