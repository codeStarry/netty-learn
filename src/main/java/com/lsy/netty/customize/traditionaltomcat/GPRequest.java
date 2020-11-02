package com.lsy.netty.customize.traditionaltomcat;

import com.lsy.netty.customize.Request;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lsy
 * @date 2020/10/30 16:17
 */
public class GPRequest implements Request {

    private String method;
    private String url;

    public GPRequest(InputStream in) {
        try {
            //获取HTTP内容
            String content = "";
            byte [] bytes = new byte[1024];
            int len = 0;
            if ((len = in.read(bytes)) > 0) {
                content = new String(bytes, 0, len);
            }

            String line = content.split("\\n")[0];
            String [] arr = line.split("\\s");

            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
