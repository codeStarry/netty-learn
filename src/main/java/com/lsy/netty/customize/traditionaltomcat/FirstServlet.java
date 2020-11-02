package com.lsy.netty.customize.traditionaltomcat;

import com.lsy.netty.customize.Request;
import com.lsy.netty.customize.Response;

/**
 * @author lsy
 * @date 2020/10/30 16:22
 */
public class FirstServlet extends GPServlet {

    @Override
    public void doGet(Request request, Response response) throws Exception {
        doPost(request, response);
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        response.write("This is First Servlet");
    }
}
