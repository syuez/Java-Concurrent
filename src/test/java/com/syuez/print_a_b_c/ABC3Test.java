package com.syuez.print_a_b_c;

import org.junit.Test;

public class ABC3Test {
    @Test
    public void abc3() {
        new Thread(ABC3::printA,"A").start();
        new Thread(ABC3::printB,"B").start();
        new Thread(ABC3::printC,"C").start();
    }
}
