package com.afterqcd.study.couchbase.client;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonLongDocument;

/**
 * Created by afterqcd on 16/7/25.
 */
public class CounterDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.248");
        Bucket bucket = cluster.openBucket("default");

        try {
            JsonLongDocument counter = bucket.counter("counter", 0, 0);
            System.out.println("Initialize or retrieve a counter with value " + counter.content());

            counter = bucket.counter("counter", 20);
            System.out.println("Increment counter by 20 and get " + counter.content());

            counter = bucket.counter("counter", -10);
            System.out.println("Decrement counter by 10 and get " + counter.content());
        } finally {
            cluster.disconnect();
        }
    }
}
