package com.lsy.netty.customize.niotomcat;

import com.lsy.netty.customize.traditionaltomcat.GPServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Netty实现tomcat
 * @author lsy
 * @date 2020/10/30 17:33
 */
public class NettyTomcat {

    private int port = 8080;
    private Map<String, GPServlet> servletMapping = new HashMap<>();
    private Properties webxml = new Properties();

    private void init() {

        try {
            //加载web.properties文件，同时初始化ServletMapping对象
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");

            webxml.load(fis);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    GPServlet obj = (GPServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        init();
        //Netty封装了NIO得Reactor模型，Boss，Worker
        //Boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //Worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    //主线程处理类，看到这样得写法，底层就是用反射
                    .channel(NioServerSocketChannel.class)
                    //子线程处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //无锁化串行编程
                            //Netty对HTTP得封装，对顺序有要求
                            //HttpResponseEncoder编码器
                            //责任链模式，双向链表 Inbound OutBound
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpResponseEncoder());
                            //HttpRequestDecoder解码器
                            pipeline.addLast(new HttpRequestDecoder());
                            //业务处理逻辑
                            pipeline.addLast(new GPTomcatHandler());
                        }
                    })
                    //针对主线程的配置，分配线程最大数量128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //针对子线程的配置 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //3.启动服务器
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("GPTomcat已启动，监听的端口是：" + this.port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 业务处理
     */
    public class GPTomcatHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest) {
                System.out.println("hello");
                HttpRequest req = (HttpRequest) msg;

                //转交给我们自己的Request实现
                NettyGPRequest request = new NettyGPRequest(ctx, req);
                NettyGPResponse response = new NettyGPResponse(ctx, req);
                //实际业务处理
                String url = request.getUrl();
                if (servletMapping.containsKey(url)) {
                    servletMapping.get(url).service(request, response);
                }else {
                    response.write("404 - Not Found");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
