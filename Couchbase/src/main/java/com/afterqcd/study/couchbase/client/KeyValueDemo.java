package com.afterqcd.study.couchbase.client;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by afterqcd on 16/7/25.
 */
public class KeyValueDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.248");
        Bucket bucket = cluster.openBucket("default");

        try {
            insert(bucket);
            get(bucket);
            upsert(bucket);
            replace(bucket);
            remove(bucket);
        } finally {
            cluster.disconnect();
        }
    }

    private static void insert(Bucket bucket) {
        JsonObject arthur = JsonObject.create()
                .put("name", "Arthur")
                .put("email", "kingarthur@couchbase.com")
                .put("interests", JsonArray.from("Holy Grail", "African Swallows"));

        JsonDocument document = JsonDocument.create("u:arthur", arthur);
        document = bucket.insert(document);
        System.out.println("insert u:arthur into default bucket and return cas " + document.cas());
    }

    private static void get(Bucket bucket) {
        JsonDocument arthur = bucket.get("u:arthur");
        System.out.println("get arthur from default bucket");
        System.out.println(arthur);
    }

    private static void upsert(Bucket bucket) {
        JsonObject arthur = JsonObject.create()
                .put("name", "Arthur")
                .put("email", "kingarthur@couchbase.com")
                .put("interests", JsonArray.from("Holy Grail", "African Swallows"));

        JsonDocument document = JsonDocument.create("u:arthur", arthur);
        document = bucket.upsert(document);
        System.out.println("upsert u:arthur to default bucket and return cas " + document.cas());
    }

    private static void replace(Bucket bucket) {
        JsonDocument arthur = bucket.get("u:arthur");
        arthur.content().getArray("interests").add("Game");
        JsonDocument newArthur = JsonDocument.create(arthur.id(), arthur.content(), arthur.cas());
        newArthur = bucket.replace(newArthur);
        System.out.println("replace u:arthur to default bucket with cas " + arthur.cas() + " and return cas " + newArthur.cas());
    }

    private static void remove(Bucket bucket) {
        bucket.remove("u:arthur");
        System.out.println("Remove u:arthur from default bucket");
    }
}
