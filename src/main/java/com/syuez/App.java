package com.syuez;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Runnable task = () -> System.out.println("Task is executed by : "
                + Thread.currentThread().getName());

        Thread t = new Thread(task, "MY_THREAD");
        t.start();

//        Executor e = Executors.newSingleThreadExecutor();
//        e.execute(task);
    }
}
