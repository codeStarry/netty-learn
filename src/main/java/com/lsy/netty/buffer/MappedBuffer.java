package com.lsy.netty.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * I/O映射缓冲区  内存映射
 * @author lsy
 * @date 2020/10/29 18:12
 */
public class MappedBuffer {

    static private final int start = 0;
    static private final int size = 1024;

    static public void main(String [] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("D:\\learnProject\\netty-learn\\src\\main\\resources\\test.txt", "rw");
        FileChannel fc = raf.getChannel();
        //把缓冲区跟文件系统进行一个映射关联
        //只要操作缓冲区里面的内容，文件内内容也会跟着改变
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
        mbb.put(0, (byte) 97);
        mbb.put(1023, (byte) 122);

        raf.close();
    }
}
