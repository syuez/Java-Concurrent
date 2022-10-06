package com.syuez.print_a_b_c;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock + 全局变量 state
 * 用 lock 来实现同步
 * 用全局变量 state 标识该哪个线程执行，不执行就释放锁
 */
public class ABC2 {
    // 可重入锁
    private final static Lock lock = new ReentrantLock();
    // 判断是否执行：1 表示应该 A 执行，2 表示应该 B 执行，3 表示应该 C 执行
    private static int state = 1;

    public static void printA() {
        // 自旋
        while (state < 4) {
            try {
                // 获取锁
                lock.lock();
                // 并发情况下，不能用 if ,要用循环判断等待条件，避免虚假唤醒
                while (state == 1) {
                    System.out.println("A");
                    state++;
                }
            } finally {
                // 要保证不执行的时候，锁能释放掉
                lock.unlock();
            }
        }
    }

    public static void printB() {
        // 要在循环里获取锁，不然线程可能会在获取到锁之前就终止了
        while (state < 4) {
            try {
                lock.lock();
                // 获取到锁，应该执行
                while (state == 2) {
                    System.out.println("B");
                    state++;
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void printC() {
        while (state < 4) {
            try {
                lock.lock();
                while (state == 3) {
                    System.out.println("C");
                    state++;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
