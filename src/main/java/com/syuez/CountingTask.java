package com.syuez;

import java.util.concurrent.Callable;

public class CountingTask implements Callable<Integer> {
    private final int id;

    public CountingTask(int id) {
        this.id = id;
    }

    @Override
    public Integer call() {
        Integer val = 0;
        for(int i = 0; i < 100; i++ ) {
            // 每个任务都会使 val 自增100次
            val++;
            System.out.println(id + " " +
                    Thread.currentThread().getName() + " " + val);
        }
        return val;
    }
}
