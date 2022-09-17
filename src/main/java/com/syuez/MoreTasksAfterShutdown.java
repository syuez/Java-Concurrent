package com.syuez;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoreTasksAfterShutdown {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(new NapTask(1));
        exec.shutdown();
        try {
            exec.execute(new NapTask(99));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
