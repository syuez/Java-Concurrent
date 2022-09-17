package com.syuez;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 工作窃取 线程池
 */
public class WorkStealingPool {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(
                Runtime.getRuntime().availableProcessors()
        );

        ExecutorService exec = Executors.newWorkStealingPool();

        for (int n = 0; n < 10; n++) {
            ShowThread showThread = new ShowThread();
            exec.execute(showThread);
        }
         
        exec.awaitTermination(1, TimeUnit.SECONDS);

    }
}

class ShowThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
