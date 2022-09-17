/*
* https://www.baeldung.com/java-executor-service-tutorial
*/
package com.syuez;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Java8ExecutorServiceIntegrationTest {
    private Runnable runnableTask;
    private Callable<String> callableTask;
    private List<Callable<String>> callableTasks;
    public Java8ExecutorServiceIntegrationTest() throws InterruptedException, ExecutionException {
        this.init();
        this.justDoIt();
    }
    private void init() {
        runnableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3000);
                System.out.println("This is runnableTask!");
                System.out.println("Task is executed by : "
                        + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(3000);
            System.out.println("This is callableTask!");
            System.out.println("Task is executed by : "
                    + Thread.currentThread().getName());
            return "Task's execution";
        };
        callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
    }
    private void justDoIt() throws InterruptedException, ExecutionException {
        this.init();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // executorService.submit(runnableTask);
        // executorService.submit(callableTask);
        // 没有 shutdown 的话，线程不会结束，即使任务已经结束
        // executorService.shutdown();

//        Future<String> future =
//                executorService.submit(callableTask);
//        executorService.shutdown();
//        TimeUnit.MILLISECONDS.sleep(10000);
//        System.out.println(future.isDone());

//        String result = executorService.invokeAny(callableTasks);
//        System.out.println(result);
//        executorService.shutdown();

//        List<Future<String>> futures = executorService.invokeAll(callableTasks);
//        for(Future<String> future : futures ) {
//            System.out.println(future.isDone());
//        }
//        executorService.shutdown();
        Future<String> future = executorService.submit(callableTask);
        future.cancel(false);
        executorService.shutdown();

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new Java8ExecutorServiceIntegrationTest();
    }
}
