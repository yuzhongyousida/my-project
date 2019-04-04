package com.myself.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: wangteng
 * @description:
 * @date:2018/5/30
 */
public class TimeClientHandler implements Runnable{
    private Selector selector;
    private SocketChannel sc;
    private String host;
    private int port;
    private volatile boolean isStop;


    public TimeClientHandler(String host, int port){
        this.host = host;
        this.port = port;

        try {
            selector = Selector.open();
            sc = SocketChannel.open();
            sc.configureBlocking(false);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        // 连接服务端并发送请求
        try {
            doConnect();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        // 读取服务端响应
        while (!isStop){
            try {
                // 每隔1000ms询问一次状态
                selector.select(1000);

                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeySet.iterator();
                SelectionKey key = null;

                while (it.hasNext()){
                    key = it.next();
                    it.remove();
                    try {
                        doInputHandle(key);
                    }catch (Exception e){
                        if (key!=null){
                            key.cancel();

                            if (key.channel()!=null){
                                key.channel().close();
                            }
                        }

                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }

        // 关闭多路复用器
        if (selector!=null){
            try {
                selector.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private void doConnect() throws IOException {

        // 如果直接连接成功，则注册到多路复用器上监听OP_READ事件，发送请求，读取响应；否则，注册到多路复用器上监听OP_COLLECT事件
        if (sc.connect(new InetSocketAddress(host, port))){
            sc.register(selector, SelectionKey.OP_READ);
            doWrite(sc);
        }else {
            sc.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        String requestStr = "what time is now?";
        byte[] bytes = requestStr.getBytes();

        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put(bytes);
        writeBuffer.flip();

        sc.write(writeBuffer);

        if (!writeBuffer.hasRemaining()){
            System.out.println("client send msg succeed");
        }

    }

    private void doInputHandle(SelectionKey key) throws IOException {

        if (key.isValid()){
            SocketChannel sc = (SocketChannel) key.channel();

            // 判断key的通道是否已完成其套接字连接操作,即是否连接成功
            if (key.isConnectable()){
                if (sc.finishConnect()){
                    // 完成连接，则发送请求
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                }else {
                    // 连接失败，则退出
                    System.exit(1);
                }
            }

            // 可读状态才读取响应
            if (key.isReadable()){
                ByteBuffer readerBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readerBuffer);

                if (readBytes>0){
                    readerBuffer.flip();

                    byte[] bytes = new byte[readerBuffer.remaining()];
                    readerBuffer.get(bytes);

                    String responseStr = new String(bytes, "UTF-8");

                    System.out.println("now time is : " + responseStr);

                    this.isStop = true;
                }else if (readBytes < 0){
                    key.cancel();
                    sc.close();
                }else {

                }
            }
        }
    }

}
