package com.afterqcd.study.couchbase.client;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import rx.Observable;

import java.util.concurrent.CountDownLatch;

/**
 * Created by afterqcd on 16/7/26.
 */
public class AsynchronousDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.248");
        Bucket bucket = cluster.openBucket("users");

        try {
            getAsync(bucket);
            getSync(bucket);
        } finally {
            cluster.disconnect();
        }
    }

    private static void getAsync(Bucket bucket) {
        System.out.println(System.currentTimeMillis());
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
                        e -> {},
                        latch::countDown
                );
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
}
