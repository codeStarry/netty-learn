package com.lsy.netty.buffer;

import java.nio.ByteBuffer;

/**
 * 只读缓冲区 Demo
 * asReadOnlyBuffer()
 * 允许读取，不允许写入；如果原缓冲区的数据发生变化，那么只读缓冲区的数据也会发生变化
 * @author lsy
 * @date 2020/10/29 17:41
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }
        //创建只读缓冲区
        ByteBuffer readonly = buffer.asReadOnlyBuffer();
        //输出
        readonly.position(0);
        readonly.limit(buffer.limit());
        System.out.println("原缓冲区未改变前：");
        while ( readonly.remaining() > 0 ) {
            System.out.print(readonly.get() + " ");
        }
        //改变原缓冲区的内容
        for (int i = 0; i < buffer.capacity(); ++i) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i, b);
        }

        readonly.position(0);
        readonly.limit(buffer.limit());

        System.out.println("\n原缓冲区改变后：");
        while ( readonly.remaining() > 0 ) {
            System.out.print(readonly.get() + " ");
        }
    }
}
