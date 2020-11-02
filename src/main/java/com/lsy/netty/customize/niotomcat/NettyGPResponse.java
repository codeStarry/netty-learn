package com.lsy.netty.customize.niotomcat;

import com.lsy.netty.customize.Response;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

/**
 * @author lsy
 * @date 2020/10/30 17:56
 */
public class NettyGPResponse implements Response {

    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public NettyGPResponse(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public void write(String out) throws Exception {
        try {
            if (null == out || out.length() == 0) {
                return;
            }
            //设置HTTP请求头信息
            FullHttpResponse response =
                    new DefaultFullHttpResponse(
                            HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(out.getBytes("UTF-8")));
            response.headers().set("Content-Type", "text/html");

            ctx.writeAndFlush(response);
        } finally {
            ctx.close();
        }
    }
}
