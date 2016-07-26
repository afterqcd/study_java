package com.afterqcd.study.couchbase.client;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;

/**
 * Created by afterqcd on 16/7/26.
 */
public class AsynchronousDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.248");
        Bucket bucket = cluster.openBucket("default");

        try {
        } finally {
            cluster.disconnect();
        }
    }
}
