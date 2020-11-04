package com.lsy.netty.manyprotobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author lsy
 * @date 2020/11/4 14:38
 */
public class MyClientHandler extends SimpleChannelInboundHandler<People.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, People.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        People.MyMessage message = null;
        int randomInt = new Random().nextInt(3);

        if (0 == randomInt) {

            message = People.MyMessage.newBuilder()
                    .setDataType(People.MyMessage.DataType.personType)
                    .setPerson(
                            People.Person.newBuilder().setName("张三").setAge(25).setAddress("浙江杭州").build())
                    .build();

        }else if (1 == randomInt) {

            message = People.MyMessage.newBuilder()
                    .setDataType(People.MyMessage.DataType.dogType)
                    .setDog(
                            People.Dog.newBuilder().setName("来福").setAge(5).build())
                    .build();

        }else {
            message = People.MyMessage.newBuilder()
                    .setDataType(People.MyMessage.DataType.catType)
                    .setCat(
                            People.Cat.newBuilder().setName("胖橘").setCity("西安").setColor("橘色").build())
                    .build();
        }

        ctx.channel().writeAndFlush(message);
    }
}
