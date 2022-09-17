package com.syuez;

import com.syuez.onjava.Nap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuittingTasks {
    public static final int COUNT = 10;
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<QuittableTask> tasks = IntStream.range(1, COUNT).mapToObj(QuittableTask::new).peek(qt -> executor.execute(qt)).collect(Collectors.toList());
//        List<QuittableTask> tasks = new ArrayList<>();
//        int bound = COUNT;
//        for (int i = 1; i < bound; i++) {
//            QuittableTask qt = new QuittableTask(i);
//            executor.execute(qt);
//            tasks.add(qt);
//        }
        new Nap(10);
        tasks.forEach(QuittableTask::quit);
//        for (QuittableTask task : tasks) {
//            task.quit();
//        }
        executor.shutdown();
    }
}
