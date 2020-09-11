package com.suitupmonkey.common.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

/**
 * 第一次写的时候没给http-codec，就没有请求到ChatroomServerHandler,
 * 添加了http-codec，虽然请求进来了，但是aggregator写成了http-aggregator,所以连接失败了，去看源码吧。
 */
public class SocketClient {

    public static void main(String[] args) throws Exception {
        run(8083);
    }

    public static void run(int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // (1)
        try {
            Bootstrap b = new Bootstrap(); // (2)
            b.group(workerGroup)
                    .channel(NioSocketChannel.class) // (3)
                    .remoteAddress("localhost",8082)
                    .handler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new SocketClientInboundHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128);          // (5)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.connect().sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully().sync();
        }
    }
}
