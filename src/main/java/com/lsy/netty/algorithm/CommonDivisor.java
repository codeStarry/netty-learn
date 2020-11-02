package com.lsy.netty.algorithm;

/**
 * 辗转相除法：求解最大公约数
 *
 * 求最小公倍数：两数乘积除以最大公约数
 *
 * @author lsy
 * @date 2020/10/30 14:28
 */
public class CommonDivisor {
    public static void main(String[] args) {
        int a = 12;
        int b = 42;
        while (a != b) {
            if (b > a) {
                b = b - a;
            }else {
                a = a - b;
            }
        }
        System.out.println("最大公约数为：" + b);

    }
}
