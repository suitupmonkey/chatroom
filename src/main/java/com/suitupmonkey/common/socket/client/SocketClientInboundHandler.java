package com.suitupmonkey.common.socket.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author：louweiwei
 * @date：2020/7/29 15:15
 * @description：客户端入栈处理器
 */
@ChannelHandler.Sharable
public class SocketClientInboundHandler extends SimpleChannelInboundHandler {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("outbound message from client", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        // 打印消息
        System.out.println("server received: " + in.toString(CharsetUtil.UTF_8));

    }

//        int input = System.in.read();
//        while(input != 'q') {
//            input = System.in.read();
//            ctx.writeAndFlush(Unpooled.copiedBuffer(input+"", CharsetUtil.UTF_8));
//        }
}
