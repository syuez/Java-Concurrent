package com.syuez.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompleteFeatureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // simpleTask();
        // serialTask();
        // andTask();
        // orTask();
        complexTask();
    }

    /**
     * 多任务
     */
    private static void complexTask() throws ExecutionException, InterruptedException {
        // 16. anyOf
        CompletableFuture future16 = CompletableFuture.anyOf(CompletableFuture.supplyAsync(()->
        {
            sleep(30);
            System.out.println("16.1 TaskA be called.");
            return "16.2 TaskA return value.";
        }), CompletableFuture.supplyAsync(()->{
            sleep(10);
            System.out.println("16.3 TaskB be called.");
            return "16.4 TaskB return value.";
        }));
        System.out.println("16.5 get: " + future16.get());
        sleep(40);

        // 17. allOf
        CompletableFuture<Void> future17 = CompletableFuture.allOf(CompletableFuture.supplyAsync(()->
        {
            sleep(30);
            System.out.println("17.1 TaskA be called.");
            return "17.2 TaskA return value.";
        }), CompletableFuture.supplyAsync(()->{
            sleep(10);
            System.out.println("17.3 TaskB be called.");
            return "17.4 TaskB return value.";
        }));
        System.out.println("17.5 get: " + future17.get()); // allOf 没有返回值
    }

    /**
     * Or 汇聚关系
     */
    private static void orTask() throws ExecutionException, InterruptedException {
        // 13. applyToEither 使用 A,B 两个异步任务优先返回的结果
        CompletableFuture<String> future13 = CompletableFuture.supplyAsync(() -> {
            sleep(20); // 虽然这个任务先执行，但是执行时间比下面的任务长，所以最后会使用下面的返回结果
            System.out.println("13.1 "); // 用于证明拿到B的结果后，A还会继续执行，并不会终止
            return "13.2 TaskA return value";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            sleep(10);
            return "13.3 TaskB return value";
        }), (returnVal) -> returnVal);
        System.out.println("13.4 get: " + future13.get());
        sleep(30);

        // 14. acceptEither 使用 A,B 两个异步任务优先返回的结果
        CompletableFuture.supplyAsync(() -> {
            sleep(20); // 虽然这个任务先执行，但是执行时间比下面的任务长，所以最后会使用下面的返回结果
            return "14.1 TaskA return value";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            sleep(10);
            return "14.2 TaskB return value";
        }), (returnVal) -> {
            System.out.println(returnVal);
        });
        sleep(30);

        // 15. runAfterEither A，B任意一个执行完后就执行C，C不关心前面任务的返回值
        CompletableFuture.supplyAsync(()->{
            sleep(20);  // 虽然这个任务先执行，但是执行时间比下面的任务长，所以最后会使用下面的返回结果
            System.out.println("15.1 TaskA be called.");
            return "15.2 TaskA return value";
        }).runAfterEither(CompletableFuture.supplyAsync(()->{
            sleep(10);
            System.out.println("15.3 TaskB be called.");
            return "15.4 TaskB return value";
        }), () -> {
            System.out.println("15.5 TaskC be called.");
        });
        sleep(30);
    }

    /**
     * And 汇聚关系
     */
    private static void andTask() throws ExecutionException, InterruptedException {
        // 10. thenCombine 合并结果
        CompletableFuture<String> future10 = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "10.1 TaskA return value ";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "10.2 TaskB return value";
        }),(taskAReturnVal, taskBReturnVal) -> taskAReturnVal + taskBReturnVal);
        System.out.println("10.3 get: " + future10.get());
        sleep(10);

        // 11. thenAcceptBoth
        CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "11.1 TaskA return value ";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "11.2 TaskB return value";
        }),(taskAReturnVal, taskBReturnVal) -> {
            System.out.println(taskAReturnVal + taskBReturnVal);
        });
        sleep(10);

        // 12. runAfterBoth A，B 都执行完后才执行 C，C 不关心前面任务的返回值
        CompletableFuture.supplyAsync(() -> {
            sleep(20); // 虽然这个任务先执行，但是执行时间比下面的任务长，所以最后会使用下面的返回结果
            System.out.println("12.1 TaskA be called.");
            return "12.2 TaskA return value";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            sleep(10);
            System.out.println("12.3 TaskB be called.");
            return "12.4 TaskB return value";
        }), () -> {
            System.out.println("12.5 TaskC be called.");
        });
        sleep(30);
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
