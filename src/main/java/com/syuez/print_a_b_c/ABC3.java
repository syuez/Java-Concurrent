package com.syuez.print_a_b_c;

/**
 * volatile 能保证变量的更改对所有线程可见
 */
public class ABC3 {
    //判断是否执行：1表示应该A执行，2表示应该B执行，3表示应该C执行
    private static volatile Integer state = 1;

    public static void printA() {
        // 通过循环，hang住线程
        while (state != 1) {}
        System.out.println("A");
        state++;
    }

    public static void printB() {
        // 通过循环，hang住线程
        while (state != 2) {}
        System.out.println("B");
        state++;
    }

    public static void printC() {
        // 通过循环，hang住线程
        while (state != 3) {}
        System.out.println("C");
        state++;
    }
}
