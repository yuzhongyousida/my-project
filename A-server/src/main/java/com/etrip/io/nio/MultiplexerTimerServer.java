package com.etrip.io.nio;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: wangteng
 * @description:
 * @date:2018/5/30
 */
public class MultiplexerTimerServer implements Runnable{
    private ServerSocketChannel serverSocketChannel ;
    private Selector selector;
    private volatile boolean stop;


    public MultiplexerTimerServer(int port){
        try {
            // 初始化多路复用器
            selector = Selector.open();

            // 初始化serverSocketChannel， 监听端口，设置为非阻塞
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);

            // serverSocketChannel注册到多路复用器Selector上，监听OP_ACCEPT事件
            SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("the timer server is started at port : " + port);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }


    public void stop(){
        this.stop = true;
    }



    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                SelectionKey key = null;

                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();

                    try {
                        handleInput(key);
                    }catch (Exception  e){
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
            }

        }

        // 多路复用器关闭之后，所有注册到其上的Channel和pipe都会被自动去注册并关闭，所以不需要重复复释放资源
        if (selector!=null){
            try {
                selector.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()){

            // 处理新接入的请求, 将accept的SocketChannel配置成非阻塞，同时注册到多路复用器上，监听OP_READ事件
            if (key.isAcceptable()){
                ServerSocketChannel ss = (ServerSocketChannel) key.channel();
                SocketChannel sc = ss.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }

            // 处理客户端请求消息（selector中的read事件触发了，则进行处理）
            if (key.isReadable()){
                // 读取客户端请求消息
                SocketChannel sc = (SocketChannel) key.channel();

                // 初始化一个capacity为1M的ByteBuffer实例
                ByteBuffer readerBuffer = ByteBuffer.allocate(1024);

                // 将SocketChannel中的请求消息读取到ByteBuffer区
                int readBytes = sc.read(readerBuffer);

                // 读取的字节数大于0
                if (readBytes > 0){
                    // 翻转，也就是让flip之后的position到limit这块区域变成之前的0到position这块，翻转就是将一个处于存数据状态的缓冲区变为一个处于准备取数据的状态
                    readerBuffer.flip();

                    // 生成一个长度大小为readerBuffer的limit - position大小的字节数组; readerBuffer.remaining()方法返回limit和position之间相对位置差
                    byte[] bytes = new byte[readerBuffer.remaining()];

                    // 将readerBuffer对象中存储在字节数组的字节全部读取到bytes中
                    readerBuffer.get(bytes);

                    // 转换为字符串
                    String requestStr = new String(bytes, "UTF-8");

                    System.out.println("the time server receive msg is : " + requestStr);

                    // 响应字符串
                    String responseStr = "now time is : " + System.currentTimeMillis();

                    // 向客户端响应
                    doWrite(sc, responseStr);
                }else if (readBytes < 0){
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                }else {
                    // 读取到0字节啥都不做
                }
            }
        }
    }

    private void doWrite(SocketChannel sc, String responseStr) throws IOException {
        if (!StringUtils.isEmpty(responseStr)){
            // 字符串转字符数组
            byte[] bytes = responseStr.getBytes();

            // 初始化一个capacity为1M的ByteBuffer作为写缓冲区
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

            // responseStr内容复制入缓冲区
            writeBuffer.put(bytes);

            // 翻转
            writeBuffer.flip();

            // 响应
            sc.write(writeBuffer);

            System.out.println("the time server response content is : " + responseStr);
        }
    }

}
