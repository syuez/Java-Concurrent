package com.syuez;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Futures {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<Integer> f = exec.submit(new CountingTask(99));
        System.out.println(f.get()); // 当你对尚未完成任务的 Future 调用 get() 方法时，调用会持续阻塞(等待)，直到结果可用
        exec.shutdown();
    }
}
