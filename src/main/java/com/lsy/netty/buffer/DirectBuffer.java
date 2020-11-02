package com.lsy.netty.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 直接缓冲区
 * allocateDirect()创建，而不是allocate()
 * @author lsy
 * @date 2020/10/29 17:55
 */
public class DirectBuffer {
    public static void main(String[] args) throws IOException {
        //从磁盘读取文件
        String infile = "D:\\learnProject\\netty-learn\\src\\main\\resources\\test.txt";
        FileInputStream fis = new FileInputStream(infile);
        FileChannel channel = fis.getChannel();

        //把读取的内容写入一个新文件
        String outfile = "D:\\learnProject\\netty-learn\\src\\main\\resources\\demo.txt";
        FileOutputStream fos = new FileOutputStream(outfile);
        FileChannel outChannel = fos.getChannel();

        //使用直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            buffer.clear();

            int r = channel.read(buffer);
            if (r == -1) {
                break;
            }

            buffer.flip();

            outChannel.write(buffer);
        }
    }
}
