import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

/**
 * Created by afterqcd on 2017/3/9.
 */
public class Program {
    public static void main(String[] args) {
        CouchbaseCluster cluster = CouchbaseCluster.create("couchbase-3.couchbase.app.svc.fxsoft.co");
        Bucket bucket = cluster.openBucket("misc");

        insert(bucket);
        get(bucket);
        remove(bucket);
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

    private static void remove(Bucket bucket) {
        bucket.remove("u:arthur");
        System.out.println("Remove u:arthur from default bucket");
    }
}
