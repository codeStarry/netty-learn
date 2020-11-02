package com.lsy.netty.customize.traditionaltomcat;

import com.lsy.netty.customize.Request;
import com.lsy.netty.customize.Response;

/**
 * 自定义servlet
 * @author lsy
 * @date 2020/10/30 16:16
 */
public abstract class GPServlet {

    /**
     * 由service()方法决定是调用doGet()还是doPost()方法
     * @param request
     * @param response
     * @throws Exception
     */
    public void service(Request request, Response response) throws Exception{
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        }else {
            doPost(request, response);
        }
    }

    public abstract void doGet(Request request, Response response) throws Exception;

    public abstract void doPost(Request request, Response response) throws Exception;
}
