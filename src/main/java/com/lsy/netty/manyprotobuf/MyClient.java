package com.lsy.netty.manyprotobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lsy
 * @date 2020/11/4 14:34
 */
public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());

            ChannelFuture future = bootstrap.connect("localhost", 8899).sync();

            future.channel().closeFuture().sync();
        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}
