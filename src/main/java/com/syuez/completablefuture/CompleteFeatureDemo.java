package com.syuez.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompleteFeatureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // simpleTask();
        serialTask();
    }

    /**
     * 串行任务
     */
    private static void serialTask() throws ExecutionException, InterruptedException {
        // 3. thenRun
        CompletableFuture.supplyAsync(()-> {
            System.out.println("3.1 supplyAsync begin");
            sleep(5);  // 用于证明B等待A结束才会执行
            return "3.2 supplyAsync end";
        }).thenRun(()-> System.out.println("3.3 thenRun be called."));
        sleep(10);

        // 4. thenApply
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "4.1 apple";
        }).thenApply(returnVal -> {
           return "4.2 " + returnVal + "-苹果";
        });
        System.out.println("4.3 get: " + future4.get());
        sleep(10);

        // 5. thenAccept
        CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "5.1 orange";
        }).thenAccept(returnVal -> System.out.println("5.2 " + returnVal + "-桔子"));
        sleep(10);

        // 6. thenCompose
        CompletableFuture<String> future6 = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "6.1 apple";
        }).thenCompose((returnVal) -> {
            return CompletableFuture.supplyAsync(() -> {
                return "6.2 " + returnVal;
            });
        });
        System.out.println("6.3 get: " + future6.get());
        sleep(10);

        // 7. whenComplete
        CompletableFuture.supplyAsync(() -> {
            sleep(5);
            if(true) { // 修改boolean值观察不同结果
                return "7.1 return value for whenComplete";
            } else {
                throw new RuntimeException("7.2 throw exception for whenComplete");
            }
        }).whenComplete((returnVal, throwable) -> {
            System.out.println("7.2 returnVal: " + returnVal); // 可以直接拿到返回值，不需要通过future.get()得到
            System.out.println("7.3 throwable: " + throwable);  // 异步任务抛出异常，并不会因为异常终止，而是会走到这里，后面的代码还会继续执行
        });
        sleep(10);

        // 8. exceptionally
        CompletableFuture<String> future8 = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            if(false) { // 修改boolean值观察不同结果
                return "8.1 return value for exceptionally";
            } else {
                throw new RuntimeException("8.2 throw exception for exceptionally");
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return "8.3 return value after dealing exception.";
        });
        System.out.println("8.4 get: " + future8.get());
        sleep(10);

        // 9. handle
        CompletableFuture<String> future9 = CompletableFuture.supplyAsync(()->{
            sleep(5);
            if (false) {  //修改boolean值观察不同结果
                return "9.1 return value for handle";
            } else {
                throw new RuntimeException("9.2 throw exception for handle");
            }
        }).handle((returnVal, throwable)->{
            System.out.println("9.3 returnVal: " + returnVal);
            System.out.println("9.4 throwable: " + throwable);
            return "9.5 new return value.";
        });
        System.out.println("9.6 get: " + future9.get());
        sleep(10);
    }

    private static void simpleTask() throws InterruptedException, ExecutionException {
        // 1. runAsync 执行一个异步任务，没有返回值
        CompletableFuture.runAsync(() -> System.out.println("1. runAsync"));
        sleep(5);

        // 2. supplyAsync 执行一个异步任务，有返回值
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("2.1 supplyAsync task be called");
            sleep(5);
            return "2.2 supplyAsync return value";
        });
        System.out.println("2.3 after supplyAsync");
        System.out.println(future2.get());
        sleep(10);
    }

    /**
     * 休眠秒数
     * @param second 秒
     */
    private static void sleep(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
