package com.afterqcd.study.rxjava;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

/**
 * Created by afterqcd on 16/7/27.
 */
public class ParallelDemo {
    private static Scheduler scheduler1 = Schedulers.from(
            Executors.newFixedThreadPool(
                    10,
                    new ThreadFactoryBuilder().setNameFormat("scheduler-1-%d").build()
            )
    );

    public static void main(String[] args) throws Exception {
        tps(100000, ParallelDemo::testWithoutIo);
        tps(500, ParallelDemo::testIoOnCurrentThread);
        tps(500, ParallelDemo::testIoOnScheduler);
        tps(50, ParallelDemo::testIoOnSchedulerWithMultiObservables);
        testThreadModel();
    }

    private static void testThreadModel() {
        Observable
                .range(0, 50)
                .flatMap(i -> {
                    System.out.println("flatMap " + i + " on " + Thread.currentThread().getName());
                    return Observable.just(i).subscribeOn(scheduler1);
                })
                .map(i -> {
                    System.out.println("map " + i + " on " + Thread.currentThread().getName());
                    return i * 2;
                })
                .toBlocking()
                .subscribe(i -> {
                    System.out.println("subscribe get " + i + " on " + Thread.currentThread().getName());
                });
    }

    private static void tps(int count, Action action) throws Exception {
        long start = System.currentTimeMillis();
        action.call(count);
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("TPS " + (1000 * count / elapsedTime));
    }

    private static void testWithoutIo(int count) {
        Observable.range(0, count)
                .map(i -> 200)
                .toBlocking()
                .subscribe(code -> {});
    }

    private static void testIoOnCurrentThread(int count) throws Exception {
        Observable.range(0, count)
                .map(i -> getPage())
                .toBlocking()
                .subscribe(code -> {});
    }

    private static void testIoOnScheduler(int count) throws Exception {
        Observable.range(0, count)
                .subscribeOn(scheduler1)
                .map(i -> getPage())
                .toBlocking()
                .subscribe(code -> {});
    }

    private static void testIoOnSchedulerWithMultiObservables(int count) throws Exception {
        Observable.range(0, count)
                .flatMap(i -> Observable.just(i).subscribeOn(scheduler1))
                .map(j -> {
                    System.out.println(Thread.currentThread().getName());
                    return getPage();
                })
                .toBlocking()
                .subscribe(code -> {});
    }

    private static Integer getPage() {
        try {
            URL url = new URL("http://172.16.185.248:8091");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return connection.getResponseCode();
        } catch (IOException e) {
            return -1;
        }
    }
}

