package com.lsy.netty.customize.niotomcat;

import com.lsy.netty.customize.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * @author lsy
 * @date 2020/10/30 17:50
 */
public class NettyGPRequest implements Request {

    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public NettyGPRequest(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public String getUrl() {
        return request.uri();
    }

    public String getMethod() {
        return request.method().name();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (null == param) {
            return null;
        }else {
            return param.get(0);
        }
    }
}
