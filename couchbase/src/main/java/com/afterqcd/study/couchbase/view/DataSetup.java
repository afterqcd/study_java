package com.afterqcd.study.couchbase.view;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.time.DateUtils;
import rx.Observable;
import rx.Subscriber;

import static java.lang.Math.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by afterqcd on 16/7/26.
 */
public class DataSetup {
    private static final Gson GSON = new Gson();

    private static final String[] COUNTRIES = new String[] {
            "USA", "GB", "FR", "ES", "PO", "BR", "RU"
    };

    private static final String[] OFFERS = new String[] {
            "324-567-343", "888-756-343", "343-645-121",
            "691-809-507", "192-343-572", "298-897-673"
    };

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.239");
        Bucket bucket = cluster.openBucket("users");

        try {
//            syncWrite(bucket);
            asyncWrite(bucket);
        } finally {
            cluster.disconnect();
        }
    }

    private static void asyncWrite(Bucket bucket) {
        System.out.println("Start async write");

        final long start = System.currentTimeMillis();

        final CountDownLatch latch = new CountDownLatch(1);
        Observable
                .range(0, 10050)
                .flatMap(i -> bucket.async().upsert(
                        JsonDocument.create(
                                Integer.toString(i),
                                JsonObject.fromJson(GSON.toJson(generateUser())
                                )
                        ))
                )
                .buffer(100)
                .subscribe(new Subscriber<List<JsonDocument>>() {
                    @Override
                    public void onStart() {
                        request(100);
                    }

                    @Override
                    public void onCompleted() {
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<JsonDocument> jsonDocuments) {
                        request(100);
                    }
                });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed");
        System.out.println("Elapsed time " + (System.currentTimeMillis() - start) + "ms");
    }

    private static void syncWrite(Bucket bucket) {
        System.out.println("Start sync write");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            bucket.upsert(JsonDocument.create(
                    Integer.toString(i), JsonObject.fromJson(GSON.toJson(generateUser()))
            ));
        }
        System.out.println("Completed");
        System.out.println("Elapsed time " + (System.currentTimeMillis() - start) + "ms");
    }

    private static Map<String, Object> generateUser() {
        Date now = new Date(System.currentTimeMillis());
        Date oneWeekAge = DateUtils.addWeeks(now, -1);
        Date joinTime = random(oneWeekAge, now);

        HashMap<String, Object> user = Maps.newHashMap();
        user.put("type", "user");
        user.put("joinDate", SDF.format(joinTime));
        user.put("lastActive", SDF.format(random(joinTime, now)));
        user.put("numberOfVisits", random(1, 5000));
        user.put("origin", COUNTRIES[random(0, 6)]);
        user.put("offers", generateOffers());
        return user;
    }

    private static String[] generateOffers() {
        int numberOfOffers = random(1, 3);
        String[] selectedOffers = new String[numberOfOffers];
        for (int i = 0; i < numberOfOffers; i++) {
            selectedOffers[i] = OFFERS[random(0, 5)];
        }
        return selectedOffers;
    }

    private static int random(int from, int to) {
        return new Double(floor(Math.random() * (to - from + 1))).intValue() + from;
    }

    private static Date random(Date from, Date to) {
        return new Date(random(from.getTime(), to.getTime()));
    }

    private static long random(long from, long to) {
        return new Double(floor(Math.random() * (to - from + 1))).longValue() + from;
    }
}
