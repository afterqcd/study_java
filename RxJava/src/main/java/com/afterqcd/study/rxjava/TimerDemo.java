package com.afterqcd.study.rxjava;

import rx.Observable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by afterqcd on 16/8/18.
 */
public class TimerDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        Observable.interval(1L, TimeUnit.MILLISECONDS)
                .subscribe(t -> {
                    run();
                    latch.countDown();
                });

        latch.await();
    }

    private static void run() {
        System.out.println("Hello RxJava");
    }
}
