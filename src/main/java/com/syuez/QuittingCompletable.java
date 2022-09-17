package com.syuez;

import com.syuez.onjava.Nap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuittingCompletable {
    public static void main(String[] args) {
        List<QuittableTask> tasks = IntStream.range(1, QuittingTasks.COUNT).mapToObj(QuittableTask::new).collect(Collectors.toList());
//        List<QuittableTask> tasks = new ArrayList<>();
//        int bound = QuittingTasks.COUNT;
//        for (int i = 1; i < bound; i++) {
//            QuittableTask quittableTask = new QuittableTask(i);
//            tasks.add(quittableTask);
//        }
        List<CompletableFuture<Void>> cfutures = tasks.stream().map(CompletableFuture::runAsync).collect(Collectors.toList());
//        List<CompletableFuture<Void>> cfutures = new ArrayList<>();
//        for (QuittableTask task : tasks) {
//            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(task);
//            cfutures.add(voidCompletableFuture);
//        }
        new Nap(10);
        for (QuittableTask task : tasks) {
            task.quit();
        }
        for (CompletableFuture<Void> cfuture : cfutures) {
            cfuture.join();
        }
    }
}
