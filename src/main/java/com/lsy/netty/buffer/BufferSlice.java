package com.lsy.netty.buffer;

import java.nio.ByteBuffer;

/**
 * 缓冲区分片 Demo
 * slice()
 * @author lsy
 * @date 2020/10/29 17:31
 */
public class BufferSlice {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        //创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer bufferSlice = buffer.slice();

        //改变子缓冲区的内容
        for (int i = 0; i < bufferSlice.capacity(); ++i) {
            byte b = bufferSlice.get(i);
            b *= 10;
            bufferSlice.put(i, b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }
    }
}
