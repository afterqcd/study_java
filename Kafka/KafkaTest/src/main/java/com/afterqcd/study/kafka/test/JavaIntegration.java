package com.afterqcd.study.kafka.test;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import rx.Observable;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class JavaIntegration {
    protected static KafkaUnit kafkaUnit;
    protected final Random random = new Random();

    @BeforeClass
    public static void createBrokers() {
        kafkaUnit = new KafkaUnit();
        kafkaUnit.setUpBrokers(1);
    }

    @AfterClass
    public static void destroyBrokers() {
        kafkaUnit.shutdown();
        kafkaUnit = null;
    }

    /**
     * Consume consumer to end and convert ConsumerRecord to KeyValueRecord.
     * @param consumer
     * @param <K>
     * @param <V>
     * @return
     */
    protected <K, V> List<KeyValueRecord<K, V>> allKeyValueRecords(KafkaConsumer<K, V> consumer) {
        return Lists.newArrayList(ConsumerUtils$.MODULE$.consumeToEnd(consumer)).stream()
                .map(KeyValueRecord::new).collect(Collectors.toList());
    }

    /**
     * Take records by count.
     * @param records
     * @param count
     * @param <K>
     * @param <V>
     * @return
     */
    protected <K, V> List<KeyValueRecord<K, V>> keyValueRecords(Observable<ConsumerRecord<K, V>> records, int count) {
        return records.take(count).map(KeyValueRecord::new).toList().toBlocking().first();
    }
}
