package com.lsy.netty.customize.traditionaltomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义web容器 传统I/O实现
 * @author lsy
 * @date 2020/10/30 16:36
 */
public class GPTomcat {

    private int port = 8080;
    private ServerSocket server;
    private Map<String, GPServlet> servletMapping = new HashMap<>();

    private Properties webxml = new Properties();
    private static final String XML_PATH = "web.properties";

    private void init() {
        try {
            //加载web.properties文件，同时初始化ServletMapping对象
            String web_info = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(web_info + XML_PATH);

            webxml.load(fis);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    //单实例，多线程
                    GPServlet obj = (GPServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        //1.加载配置文件，初始化ServletMapping
        init();

        try {
            server = new ServerSocket(this.port);
            System.out.println("GPTomcat已启动，监听得端口是：" + this.port);

            //2.等待用户请求，用一个死循环来等待用户请求
            while (true) {
                Socket client = server.accept();
                //3.HTTP请求，发送得数据就是字符串---有规律的字符串（HTTP）
                process(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket client) throws Exception{
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();
        //4.Request(InputStream)/Response(OutputStream)
        GPRequest request = new GPRequest(is);
        GPResponse response = new GPResponse(os);
        //5.从协议内容中获得URL，把相应得Servlet用反射进行实例化
        String url = request.getUrl();

        if (servletMapping.containsKey(url)) {
            //6.调用实例化对象得service()方法，执行具体得逻辑doGet()/doPost()方法
            servletMapping.get(url).service(request, response);
        }else {
            response.write("404 - Not Found");
        }

        os.flush();
        os.close();

        is.close();
        client.close();
    }
}
