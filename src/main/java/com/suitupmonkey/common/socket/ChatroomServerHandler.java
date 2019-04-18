package com.suitupmonkey.common.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatroomServerHandler extends SimpleChannelInboundHandler {


    private static final Logger logger = LoggerFactory.getLogger(ChatroomServerHandler.class);

    //服务端对握手做开启和关闭的基类
    private WebSocketServerHandshaker handshaker;

    //处理请求：
    // 1.http请求升级为websocket
    //2.处理消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            handleHttpRequest(ctx,(FullHttpRequest)msg);//处理http请求
        }
        if(msg instanceof WebSocketFrame){//WebSocketFrame是干什么的暂时不清楚
            handleWebSocket(ctx,(WebSocketFrame)msg);//处理websocket消息
        }
    }

    //处理http请求
    //1.失败，返回结果。
    //2.WebSocketServerHandshakerFactory，WebSocketServerHandshaker 做握手处理。
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        //1.解码失败，或者请求头没有Upgrade:WebSocket
        boolean decodeSuccess = request.decoderResult().isSuccess();
        if(!decodeSuccess){
            response(ctx,request,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }

        //2.获取地址,WebSocketServerHandshakerFactory 用于自动检测websocket协议版本，通过webSocketURL来指定连接地址。
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:/websocket",null,false
        );

        handshaker = handshakerFactory.newHandshaker(request);//获取WebSocketServerHandShaker

        if(handshaker == null){
            //不支持
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else {
            //3.握手
            handshaker.handshake(ctx.channel(),request);
        }
    }


    private void response(ChannelHandlerContext ctx, FullHttpRequest request,
                          DefaultFullHttpResponse response) {
        int responseCode = response.status().code();
        //不成功
        if(!"200".equals(responseCode)){
            ByteBuf buf = Unpooled.copiedBuffer(responseCode + "", CharsetUtil.UTF_8);
            ByteBuf content = response.content();
            content.writeBytes(buf);
            buf.release();
        }

        ChannelFuture channelFuture = ctx.channel().writeAndFlush(request);
        //如果不是keep alive，关闭连接。
        if(!isKeepAlive() || !"200".equals(responseCode)){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }

    }

    private static boolean isKeepAlive(){
        return false;
    }

    //处理websocket消息
    private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) throws Exception {
        //关闭请求
        if(webSocketFrame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(),(CloseWebSocketFrame)webSocketFrame.retain());//retain好像不用也可以
        }

        //ping请求
        if(webSocketFrame instanceof PingWebSocketFrame){
            //Return the data which is held by this {@link ByteBufHolder}.
            ByteBuf content = webSocketFrame.content();
            ByteBuf buf = content.retain();
            //Creates a new pong frame with the specified binary data.
            PongWebSocketFrame pongWebSocketFrame = new PongWebSocketFrame(buf);
            ctx.channel().write(pongWebSocketFrame);
            return;
        }

        //text only，不支持二进制消息。
        if(!(webSocketFrame instanceof TextWebSocketFrame)){
            throw new Exception("text only");
        }

        //客户端发来的消息/Returns the text data in this frame
        String msg = ((TextWebSocketFrame) webSocketFrame).text();
        ctx.write(new TextWebSocketFrame(msg));//处理
        ctx.flush();
    }



    //处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}