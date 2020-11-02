package com.lsy.netty.customize.traditionaltomcat;

import com.lsy.netty.customize.Response;

import java.io.OutputStream;

/**
 * @author lsy
 * @date 2020/10/30 16:17
 */
public class GPResponse implements Response {

    private OutputStream out;

    public GPResponse(OutputStream out) {
        this.out = out;
    }

    public void write(String s) throws Exception{
        //输出遵循HTTP
        //状态码为200
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(s);
        out.write(sb.toString().getBytes());
    }
}
