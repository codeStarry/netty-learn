package com.lsy.netty.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ByteBuffer Demo
 * 理解：
 * position：指定下一个将要被写入或读取的元素索引，值由put/get()方法自动更新，创建新buffer时position被初始化为0
 * limit：指定还有多少数据需要写出（在从缓冲区写入通道时），或还有多少空间可以写入（在从通道写入缓冲区时）
 * capacity：指定了可以在缓冲区存储的最大容量
 * @author lsy
 * @date 2020/10/29 16:56
 */
public class ByteBufferDemo {
    public static void main(String[] args) throws IOException {
        //用文件I/O处理
        FileInputStream fis = new FileInputStream("D:/learnProject/netty-learn/src/main/resources/test.txt");
        //创建文件的操作通道
        FileChannel channel = fis.getChannel();
        //分配一个10大小的缓冲区，其实就是分配一个10个大小的Byte数组
        ByteBuffer buffer = ByteBuffer.allocate(10);
        output("初始化", buffer);
        //先读一下
        channel.read(buffer);
        output("调用read()", buffer);
        //准备操作之前，先锁定操作范围
        buffer.flip();
        output("调用flip()", buffer);
        //判断有没有可读数据
        while (buffer.remaining() > 0) {
            byte b = buffer.get();

        }
        output("调用get()", buffer);

        //可以理解为解锁
        buffer.clear();
        output("调用clear()", buffer);
        //关闭管道
        fis.close();
    }

    /**
     * 把缓冲区中的实时状态打印出来
     * @param step
     * @param buffer
     */
    public static void output(String step, Buffer buffer) {
        System.out.print(step + " : ");
        //容量，数组大小
        System.out.print("capacity：" + buffer.capacity() + "，");
        //当前操作数据所在位置，也可以叫作游标
        System.out.print("position：" + buffer.position() + "，");
        //锁定值，flip，数据操作范围索引只能在position - limit之间
        System.out.print("limit：" + buffer.limit());
        System.out.println();
    }
}
