package com.lsy.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AIO服务端
 * @author lsy
 * @date 2020/10/29 15:55
 */
public class AIOServer {

    private final int port;

    public static void main(String[] args) {
        int port = 8000;
        new AIOServer(port);
    }

    public AIOServer(int port) {
        this.port = port;
        listen();
    }

    private void listen() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            AsynchronousChannelGroup threadGroup =
                    AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);

            final AsynchronousServerSocketChannel server =
                    AsynchronousServerSocketChannel.open(threadGroup);

            server.bind(new InetSocketAddress(port));
            System.out.println("服务已启动，监听端口：" + port);

            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    System.out.println("I/O操作成功，开始获取数据");
                    buffer.clear();
                    try {
                        result.read(buffer).get();
                        buffer.flip();
                        result.write(buffer);
                        buffer.flip();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }finally {
                        try {
                            result.close();
                            server.accept(null, this);
                        } catch (IOException e) {
                            System.out.println(e.toString());
                        }
                    }
                    System.out.println("操作完成");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("I/O操作失败：" + exc);
                }
            });
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
