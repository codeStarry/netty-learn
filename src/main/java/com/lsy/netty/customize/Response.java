package com.lsy.netty.customize;

/**
 * @author lsy
 * @date 2020/10/30 18:14
 */
public interface Response {

    void write(String out) throws Exception;
}
