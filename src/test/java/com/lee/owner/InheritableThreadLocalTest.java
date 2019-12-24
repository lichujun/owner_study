package com.lee.owner;

import java.util.concurrent.CountDownLatch;

/**
 * @author joseph.li
 * @date 2019-12-24 17:56
 */
public class InheritableThreadLocalTest {

    private static final ThreadLocal<Integer> local = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            local.set(53535);
            System.out.println(Thread.currentThread() + ":" + local.get());
            countDownLatch.countDown();
            new Thread(() -> {
                System.out.println(Thread.currentThread() + ":" + local.get());
                countDownLatch.countDown();
            }).start();
        }).start();
        countDownLatch.await();
    }
}
