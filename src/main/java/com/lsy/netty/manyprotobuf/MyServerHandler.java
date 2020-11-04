package com.lsy.netty.manyprotobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lsy
 * @date 2020/11/4 14:34
 */
public class MyServerHandler extends SimpleChannelInboundHandler<People.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, People.MyMessage msg) throws Exception {
        People.MyMessage.DataType dataType = msg.getDataType();

        if (dataType == People.MyMessage.DataType.personType) {

            People.Person person = msg.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.getAddress());

        }else if (dataType == People.MyMessage.DataType.dogType) {

            People.Dog dog = msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());

        }else {
            People.Cat cat = msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getColor());
            System.out.println(cat.getCity());
        }
    }
}
