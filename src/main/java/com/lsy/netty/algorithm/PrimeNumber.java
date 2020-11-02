package com.lsy.netty.algorithm;

/**
 * 埃拉托斯特尼筛法：判定素数
 * @author lsy
 * @date 2020/10/30 14:32
 */
public class PrimeNumber {
    public static void main(String[] args) {
        //PrimeNumber.chickenRabbit();
        PrimeNumber number = new PrimeNumber();
        number.test();
    }

    public void test() {
        String s = this.getClass().getResource("/").getPath();
        System.out.println(s);
    }

    public static void isPrimeNumber() {
        int a = 91;
        for (int i = 2; i < a; i++) {
            if ((a % i) == 0) {
                System.out.println(a + "，是素数");
                return;
            }
        }
        System.out.println(a + "，不是素数");
    }

    /**
     * 鸡兔同笼
     * 鸡和兔子共计10只，把他们的脚加起来攻击32只
     * 问鸡多少只，兔子多少只
     */
    public static void chickenRabbit() {
        //设鸡为x只，兔子为y只，可得方程
        //x + y = 10；2x + 4y = 32
        String out = "鸡：%s只；兔子：%s只";
        for (int x = 0; x <= 10; x++) {
            for (int y = 0; y <= 10; y++) {
                int a = x + y;
                int b = (2 * x) + (4 * y);
                if (a == 10 && b == 32) {
                    System.out.println(String.format(out, x, y));
                }
            }
        }
    }
}
