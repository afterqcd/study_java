package com.afterqcd.study.couchbase.client;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * Created by afterqcd on 16/7/21.
 */
public class N1qlDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.239");
        Bucket bucket = cluster.openBucket("default");
        Bucket order = cluster.openBucket("order");

        try {
            upsert(bucket);
            query(bucket);
            queryOrder(order);
        } finally {
            cluster.disconnect();
        }
    }

    private static void upsert(Bucket bucket) {
        JsonObject arthur = JsonObject.create()
                .put("name", "Arthur")
                .put("email", "kingarthur@couchbase.com")
                .put("interests", JsonArray.from("Holy Grail", "African Swallows"));

        bucket.upsert(JsonDocument.create("u:arthur", arthur));
        System.out.println("upsert u:arthur to default bucket");
    }

    private static void query(Bucket bucket) {
        JsonObject params = JsonObject.create()
                .put("interest", JsonArray.from("holy grail"));
        N1qlQuery query = N1qlQuery.parameterized(
                "select *, META(`default`).id from `default`" +
                        " where any i in interests satisfies lower(i) in $interest end",
                params
        );

        N1qlQueryResult result = bucket.query(query);
        System.out.println("query by N1ql from default bucket");
        for (N1qlQueryRow user : result) {
            System.out.println(user.value());
        }
    }

    private static void queryOrder(Bucket bucket) {
        Collection<String> hashes = Lists.newArrayList("-1000082692219144982");
        JsonObject params = JsonObject.create()
                .put("hashes", JsonArray.from(hashes.toArray()));
        N1qlQuery query = N1qlQuery.parameterized(
                "select `order`.* from `order`" +
                        " where originalCustomerHash in $hashes",
                params,
                N1qlParams.build().adhoc(false)
        );


        N1qlQueryResult result = bucket.query(query);
        System.out.println("query by N1ql from order bucket");
        for (N1qlQueryRow order : result) {
            System.out.println(order.value());
        }
    }
}
