package com.syuez.print_a_b_c;

import java.util.concurrent.Semaphore;

/**
 * 线程间同步，可以使用信号量 Semaphore
 * 使用 acquire() 获取许可，如果没有可用的许可，
 * 线程进入阻塞等待状态，使用 release() 释放许可
 */
public class ABC6 {
    private static Semaphore semaphoreB = new Semaphore(0);
    private static Semaphore semaphoreC = new Semaphore(0);

    public static void printA() {
        System.out.println("A");
        semaphoreB.release();
    }

    public static void printB() throws InterruptedException {
        semaphoreB.acquire();
        System.out.println("B");
        semaphoreC.release();
    }

    public static void printC() throws InterruptedException {
        semaphoreC.acquire();
        System.out.println("C");
    }


}
