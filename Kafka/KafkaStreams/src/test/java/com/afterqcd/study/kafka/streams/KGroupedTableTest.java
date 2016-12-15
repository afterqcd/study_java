package com.afterqcd.study.kafka.streams;

import static com.afterqcd.study.kafka.serde.UnifySerdes.serde;

import com.afterqcd.study.kafka.StreamsIntegration;
import com.afterqcd.study.kafka.clients.producer.IProducer;
import com.afterqcd.study.kafka.test.KeyValueRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.junit.Test;
import rx.subjects.PublishSubject;

import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/9.
 */
public class KGroupedTableTest extends StreamsIntegration {
    private static final String CLICKS_TOPIC = "clicks";

    @Test
    public void testReduce() throws Exception {
        kafkaUnit.createCompactTopic(CLICKS_TOPIC, 3, 1);

        KStreamBuilder builder = new KStreamBuilder();
        KTable<String, Integer> clicks
                = builder.table(serde(String.class), serde(Integer.class), CLICKS_TOPIC, "clicks");
        KTable<String, Integer> reducedClicks = clicks.groupBy(KeyValue::pair, serde(String.class), serde(Integer.class))
                .reduce((v1, v2) -> {
                    System.out.println("+" + v1 + " " + v2);
                    return v1 + v2;
                }, (v1, v2) -> {
                    System.out.println("-" + v1 + " " + v2);
                    return v1 - v2;
                }, "reduced-clicks");

        PublishSubject<KeyValueRecord<String, Integer>> results = PublishSubject.create();
        reducedClicks.foreach((k, v) -> results.onNext(new KeyValueRecord<>(k, v)));

        KafkaStreams streams = new KafkaStreams(builder, streamsProps());
        streams.cleanUp();
        streams.start();

        IProducer<String, Integer> producer = producer(CLICKS_TOPIC, String.class, Integer.class);
        producer.sendDefault("A", 1);
        producer.sendDefault("B", 2);
        producer.sendDefault("A", 5);
        producer.sendDefault("B", 6);
        producer.flush();

        results.take(4).toList().toBlocking().first().forEach(kv -> System.out.println(kv.key() + ": " + kv.value()));

        streams.close();
    }

    private Properties streamsProps() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kgroupedtable-test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers());
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, kafkaUnit.zkUnit().zkConnect());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
        return props;
    }
}
