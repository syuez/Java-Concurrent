package com.syuez;

import com.syuez.onjava.Nap;

import java.util.concurrent.atomic.AtomicBoolean;

public class QuittableTask implements Runnable{
    private final int id;

    public QuittableTask(int id) {
        this.id = id;
    }

    private AtomicBoolean running = new AtomicBoolean(true);

    public void quit() {
        running.set(false);
    }

    @Override
    public void run() {
        while (running.get()) {
            new Nap(0.1);

        }
        // 在任务退出后才会执行以下的输出
        System.out.println(id + "退出了");
    }
}
