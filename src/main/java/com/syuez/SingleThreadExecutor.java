package com.syuez;

import com.syuez.onjava.Nap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        IntStream.range(0,10).mapToObj(NapTask::new).forEach(exec::execute); // 和下面的循环是等价的
//        for (int i = 0; i < 10; i++) {
//            NapTask napTask = new NapTask(i);
//            exec.execute(napTask);
//        }
        System.out.println("所有任务都已经提交");
        exec.shutdown();
        while (!exec.isTerminated()) {
            System.out.println(
                    Thread.currentThread().getName() + " 等待中止"
            );
            new Nap(0.1);
        }
    }
}
