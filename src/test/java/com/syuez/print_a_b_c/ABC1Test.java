package com.syuez.print_a_b_c;

import org.junit.Test;

public class ABC1Test {
    @Test
    public void abc1() {
        // 线程 A
        new Thread(ABC1::printA,"A").start();
        // 线程 B
        new Thread(() -> {
            try {
                ABC1.printB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();
        // 线程 C
        new Thread(() -> {
            try {
                ABC1.printC();
            } catch (InterruptedException e) {

            }
        },"C").start();
    }
}
