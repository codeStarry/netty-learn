package com.lsy.netty.customize.niotomcat;

/**
 * @author lsy
 * @date 2020/10/30 18:23
 */
public class Application {
    public static void main(String[] args) {
        new NettyTomcat().start();
    }
}
