package com.syuez;

public class InterferingTask implements Runnable{
    private final int id;

    private static Integer val = 0;

    public InterferingTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(int i = 0; i < 100; i++ ) {
            // 每个任务都会使 val 自增100次
            val++;
            System.out.println(id + " " +
                    Thread.currentThread().getName() + " " + val);
        }
    }
}
