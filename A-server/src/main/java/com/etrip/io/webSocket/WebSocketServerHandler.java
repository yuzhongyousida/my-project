package com.etrip.io.webSocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaderUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaderUtil.setContentLength;

/**
 * @author: wangteng
 * @description:
 * @date:2018/6/10
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>{

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        // HTTP消息接入
        if (msg instanceof FullHttpRequest){
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }

        // webSocket消息接入
        else if (msg instanceof WebSocketFrame){
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


    /**
     * http消息处理
     * @param ctx
     * @param request
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request){
        // 校验前一个解码是否成功，请求是否是webSocket请求的握手HTTP请求，如果解码失败，则返回异常
        if (!request.decoderResult().isSuccess()
                || !"websocket".equals(request.headers().get("Upgrade"))){
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 构造握手响应返回(本地测试)
        WebSocketServerHandshakerFactory wsFactory =
                new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);

        handshaker = wsFactory.newHandshaker(request);

        if (handshaker==null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else {
            handshaker.handshake(ctx.channel(), request);
        }
    }

    /**
     * webSocket消息处理
     * @param ctx
     * @param request
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame request){
        // 关闭链路指令
        if (request instanceof  CloseWebSocketFrame){
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) request.retain());
            return;
        }

        // ping指令消息
        if (request instanceof PingWebSocketFrame) {
            ctx.channel().write(request.content().retain());
            return;
        }

        // 本例只支持文本消息
        if (!(request instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(String.format("%s frame type not supported", request.getClass().getName()));
        }

        // 返回应答消息
        String requestStr = ((TextWebSocketFrame) request).text();
        ctx.write(new TextWebSocketFrame(requestStr + "欢迎使用netty webSocket 服务，现在时间是：" + System.currentTimeMillis()));

    }



    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response){
        // 返回应答给客户单
        if (response.status().code()!=200){
            ByteBuf buffer = Unpooled.copiedBuffer(response.status().codeAsText().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buffer);
            buffer.release();
            setContentLength(response, response.content().readableBytes());
        }

        // 如果不是keep-alive，则关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(response);
        if (!isKeepAlive(response) || response.status().code()!=200){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
