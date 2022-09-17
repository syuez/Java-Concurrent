package com.syuez;

import com.syuez.onjava.Nap;

public class NapTask implements Runnable{
    private final int id;

    public NapTask(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        new Nap(0.1); // 秒
        System.out.println(this + " " +
                Thread.currentThread().getName());
    }
    @Override
    public String toString() {
        return "NapTask[" + id + "]";
    }
}
