package com.lsy.netty.thirdexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author lsy
 * @date 2020/10/28 17:52
 */
public class MyChatClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyChatClientInitializer());

            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for (; ;) {
                channel.writeAndFlush(reader.readLine() + "\r\n");
            }

        } finally {
            group.shutdownGracefully();
        }

    }
}
