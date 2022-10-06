package com.syuez.print_a_b_c;

import org.junit.Test;

public class ABC2Test {
    @Test
    public void abc2() {
        new Thread(ABC2::printA,"A").start();
        new Thread(ABC2::printB,"B").start();
        new Thread(ABC2::printC,"C").start();
    }
}
