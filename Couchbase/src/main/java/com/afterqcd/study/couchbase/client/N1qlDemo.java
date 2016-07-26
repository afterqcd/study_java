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
import com.couchbase.client.java.query.SimpleN1qlQuery;

/**
 * Created by afterqcd on 16/7/21.
 */
public class N1qlDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.248");
        Bucket bucket = cluster.openBucket("default");

        try {
            upsert(bucket);
            query(bucket);
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
                .put("interest", "holy grail");
        N1qlQuery query = N1qlQuery.parameterized(
                "select *, META(`default`).id from `default`" +
                        " where any i in interests satisfies lower(i) = $interest end",
                params,
                N1qlParams.build().adhoc(false)
        );

        N1qlQueryResult result = bucket.query(query);
        System.out.println("query by N1ql from default bucket");
        for (N1qlQueryRow user : result) {
            System.out.println(user.value());
        }
    }
}
