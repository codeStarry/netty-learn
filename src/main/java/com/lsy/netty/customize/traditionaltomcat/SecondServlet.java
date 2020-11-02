package com.lsy.netty.customize.traditionaltomcat;

import com.lsy.netty.customize.Request;
import com.lsy.netty.customize.Response;

/**
 * @author lsy
 * @date 2020/10/30 16:24
 */
public class SecondServlet extends GPServlet{
    @Override
    public void doGet(Request request, Response response) throws Exception {
        this.doPost(request, response);
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        response.write("This is Second Servlet");
    }
}
