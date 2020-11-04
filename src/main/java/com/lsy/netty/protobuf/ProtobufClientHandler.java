package com.lsy.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lsy
 * @date 2020/11/4 11:54
 */
public class ProtobufClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三")
                .setAge(18)
                .setAddress("杭州")
                .build();

        ctx.channel().writeAndFlush(student);
    }
}
