package com.syuez.completablefuture;

import java.util.concurrent.*;

public class CompletableFutureLongRunningUnitTest {
    public CompletableFutureLongRunningUnitTest() throws InterruptedException, ExecutionException {
        // Future<String> completableFuture = calculateAsync();
        // Future<String> completableFuture = CompletableFuture.completedFuture("Hello");
//        Future<String> future = calculateAsyncWithCancellation();
//        String result = future.get();
//        System.out.println(result);
        Future<String> future = calculateAsyncWithCancellation();
        future.get();
    }

    private Future<String> calculateAsyncWithCancellation()  throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
//        ExecutorService executor = Executors.newCachedThreadPool();
//        executor.submit(() -> {
//                    Thread.sleep(5000);
//                    completableFuture.cancel(false);
//                    return null;
//                });
//        executor.shutdown();
        Executors.newCachedThreadPool()
                .submit(() -> {
                    Thread.sleep(5000);
                    completableFuture.cancel(true);
                    return null;
                });
        return completableFuture;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> System.out.println(s1 + s2));
    }
}
