package com.afterqcd.study.couchbase.client;

import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.subdoc.DocumentFragment;

/**
 * Created by afterqcd on 16/7/25.
 */
public class SubDocumentDemo {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("172.16.185.248");
        Bucket bucket = cluster.openBucket("default");

        try {
            upsertExample(bucket);
            retrieve(bucket);
            mutate(bucket);
            arrayOperate(bucket);
        } finally {
            cluster.disconnect();
        }
    }

    private static void upsertExample(Bucket bucket) {
        JsonObject customer = JsonObject.fromJson("{\n" +
                "  \"name\": \"Douglas Reynholm\",\n" +
                "  \"email\": \"douglas@reynholmindustries.com\",\n" +
                "  \"addresses\": {\n" +
                "    \"billing\": {\n" +
                "      \"line1\": \"123 Any Street\",\n" +
                "      \"line2\": \"Anytown \",\n" +
                "      \"country\": \"United Kingdom\"\n" +
                "    },\n" +
                "    \"delivery\": {\n" +
                "      \"line1\": \"123 Any Street\",\n" +
                "      \"line2\": \"Anytown \",\n" +
                "      \"country\": \"United Kingdom\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"purchases\": {\n" +
                "    \"complete\": [\n" +
                "      339, 976, 442, 666\n" +
                "    ],\n" +
                "    \"abandoned\": [\n" +
                "      157, 42, 999\n" +
                "    ]\n" +
                "  }\n" +
                "}");
        JsonDocument document = JsonDocument.create("c:dr", customer);
        document = bucket.upsert(document);
        System.out.println("upsert c:dr to default bucket => " + document);
    }

    private static void retrieve(Bucket bucket) {
        DocumentFragment<Lookup> fragment = bucket.lookupIn("c:dr")
                .get("addresses.billing.country")
                .exists("purchases.complete[1]")
                .execute();
        System.out.println("get(addresses.billing.country) => " + fragment.content(0));
        System.out.println("exists(purchases.complete[1]) => " + fragment.content(1));
    }

    private static void mutate(Bucket bucket) {
        bucket.mutateIn("c:dr")
                .upsert("fax", "775-867-5309", false)
                .replace("email", "doug96@hotmail.com")
                .execute();

        DocumentFragment<Lookup> fragment = bucket.lookupIn("c:dr")
                .get("fax")
                .get("email")
                .execute();
        System.out.println("upsert('fax', '775-867-5309') => " + fragment.content(0));
        System.out.println("replace('email', 'doug96@hotmail.com') => " + fragment.content(1));
    }

    private static void arrayOperate(Bucket bucket) {
        bucket.mutateIn("c:dr")
                .arrayAppend("purchases.complete", 777, false)
                .arrayAddUnique("purchases.abandoned", 45, false)
                .execute();

        DocumentFragment<Lookup> fragment = bucket.lookupIn("c:dr")
                .get("purchases.complete")
                .get("purchases.abandoned")
                .execute();
        System.out.println("arrayAppend('purchases.complete', 777) => " + fragment.content(0));
        System.out.println("arrayAddUnique('purchases.abandoned', 45) => " + fragment.content(1));
    }
}
