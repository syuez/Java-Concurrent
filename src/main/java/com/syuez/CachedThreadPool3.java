package com.syuez;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CachedThreadPool3 {
    /**
     * 抽取结果
     * @param f 任务
     * @return 结果
     */
    public static Integer extractResult (Future<Integer> f) {
        try {
            return f.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        List<CountingTask> tasks = IntStream.range(0,10).mapToObj(CountingTask::new).collect(Collectors.toList()); // 和下面的循环等价
//        List<CountingTask> tasks = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            CountingTask countingTask = new CountingTask(i);
//            tasks.add(countingTask);
//        }

        List<Future<Integer>> futures = exec.invokeAll(tasks); // invokeAll() 启动所有的 Callable

        Integer sum = futures.stream().map(CachedThreadPool3::extractResult).reduce(0,Integer::sum); // 和下面的循环等价
//        Integer sum = 0;
//        for (Future<Integer> future : futures) {
//            Integer integer = extractResult(future);
//            sum = sum + integer;
//        }

        System.out.println("sum = " + sum);
        exec.shutdown();
    }


}
