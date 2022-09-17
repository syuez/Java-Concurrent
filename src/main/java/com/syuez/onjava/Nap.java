package com.syuez.onjava;

import java.util.concurrent.TimeUnit;

public class Nap {
    public Nap(double t) { // 秒
        try {
            TimeUnit.MILLISECONDS.sleep((int)(1000 * t));
            System.out.println("休眠" + t + "秒");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Nap(double t, String msg) {
        this(t);
        System.out.println(msg);
    }
}
