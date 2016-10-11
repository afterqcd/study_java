package com.afterqcd.study.couchbase.client;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import rx.Observable;
import rx.Subscriber;

import java.util.List;

/**
 * Created by afterqcd on 16/9/12.
 */
public class GetPerformanceDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.239");
        Bucket bucket = cluster.openBucket("users");
        try {
            getPerformance(bucket);
        } finally {
            cluster.disconnect();
        }
    }

    private static void getPerformance(Bucket bucket) {
        while (true) {
            Observable.range(0, 50000)
                    .map(Object::toString)
                    .flatMap(id -> bucket.async().get(id))
                    .buffer(100)
                    .subscribe(new Subscriber<List<JsonDocument>>() {
                        @Override
                        public void onStart() {
                            request(100);
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<JsonDocument> documents) {
                            request(100);
                        }
                    });
        }
    }
}
