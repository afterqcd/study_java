package com.afterqcd.study.couchbase.client;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by afterqcd on 16/7/26.
 */
public class AsynchronousDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.248");
        Bucket bucket = cluster.openBucket("users");

        try {
            getSync(bucket);
            getAsync(bucket);
            getAsyncWithRetry(bucket);
        } finally {
            cluster.disconnect();
        }
    }

    private static void getSync(Bucket bucket) {
        Observable
                .range(2000, 50)
                .map(i -> Integer.toString(i))
                .map(i -> {
                    System.out.println("to get user " + i + " at " + System.currentTimeMillis());
                    return bucket.get(i);
                })
                .subscribe(
                        u -> System.out.println("got arthur " + u.id() + " at " + System.currentTimeMillis())
                );
    }

    private static void getAsync(Bucket bucket) {
        final CountDownLatch latch = new CountDownLatch(1);

        Observable
                .range(1000, 50)
                .map(i -> Integer.toString(i))
                .flatMap(i -> {
                    System.out.println("to get user " + i + " at " + System.currentTimeMillis());
                    return bucket.async().get(i);
                })
                .subscribe(
                        u -> System.out.println("got user " + u.id() + " at " + System.currentTimeMillis()),
                        e -> {
                        },
                        latch::countDown
                );

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void getAsyncWithRetry(Bucket bucket) {
        System.out.println("Begin getAsyncWithRetry");

        final CountDownLatch latch = new CountDownLatch(1);

        Observable
                .range(1000, 10)
                .map(i -> Integer.toString(i))
                .concatMap(i -> bucket
                        .async()
                        .get(i)
                        .doOnNext(d -> {
                            System.out.println("to get user " + i);
                            if (new Random().nextInt(4) + 1 == 1) {
                                System.out.println("Failed to get " + d.id());
                                throw new RuntimeException("");
                            }
                        })
                        .retryWhen(attempts ->
                                attempts.zipWith(Observable.range(1, 2), (n, j) -> j)
                                        .flatMap(j -> {
                                            System.out.println("delay retry by " + j + " ms to get user " + i);
                                            return Observable.timer(j, TimeUnit.MILLISECONDS);
                                        })
                        ))
                .subscribe(
                        u -> System.out.println("got user " + u.id()),
                        e -> {
                        },
                        latch::countDown
                );

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
