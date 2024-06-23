package com.privacity.server.encrypt.pool2.copy;

import java.util.List;

public class ThreadUtil {
    public static void waitForAllThreadsToComplete(List<Thread> threads) {
        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sleep(long interval) {
        try {
            // Wait for some time to demonstrate threads
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
