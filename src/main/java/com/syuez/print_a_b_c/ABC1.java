package com.syuez.print_a_b_c;

/**
 * synchronized + wait + notify
 * 打印的时候需要获取锁
 * 打印 B 的线程需要等待打印 A 线程执行完，打印 C 的线程需要等待打印 B 线程执行完
 */
public class ABC1 {
    // 锁住的对象
    private final static Object lock = new Object();
    // A 是否已经执行
    private static boolean aExecuted = false;
    // B 是否已经执行
    private static boolean bExecuted = false;

    public static void printA() {
        synchronized (lock) {
            System.out.println("A");
            aExecuted = true;
            // 唤醒所有等待线程
            lock.notifyAll();
        }
    }

    public static void printB() throws InterruptedException {
        synchronized (lock) {
            // 获取到锁，但是要等 A 执行
            while (!aExecuted) {
                lock.wait();
            }
            System.out.println("B");
            bExecuted = true;
            lock.notifyAll();
        }
    }

    public static void printC() throws InterruptedException {
        synchronized (lock) {
            // 获取到锁，但是要等 B 执行
            while (!bExecuted) {
                lock.wait();
            }
            System.out.println("C");
        }
    }
}
