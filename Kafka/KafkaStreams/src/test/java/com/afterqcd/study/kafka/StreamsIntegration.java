package com.afterqcd.study.kafka;

import com.afterqcd.study.kafka.clients.consumer.JavaConsumersBuilder;
import com.afterqcd.study.kafka.clients.producer.IProducer;
import com.afterqcd.study.kafka.clients.producer.JavaProducerBuilder;
import com.afterqcd.study.kafka.test.JavaIntegration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by afterqcd on 2016/12/9.
 */
public class StreamsIntegration extends JavaIntegration {
    /**
     * Create producer.
     * @param topic
     * @param keyClz
     * @param valueClz
     * @param <K>
     * @param <V>
     * @return
     */
    protected <K, V> IProducer<K, V> producer(String topic,
                                              Class<K> keyClz, Class<V> valueClz) {
        return JavaProducerBuilder.newBuilder(keyClz, valueClz)
                .clientId(topic + "-producer-" + random.nextInt())
                .bootstrapServers(kafkaUnit.bootstrapServers())
                .defaultTopic(topic)
                .build();
    }

    /**
     * Observable records from specified topic.
     * @param topic
     * @param keyClz
     * @param valueClz
     * @param <K>
     * @param <V>
     * @return
     */
    protected <K, V> Observable<ConsumerRecord<K, V>> records(String topic,
                                                              Class<K> keyClz, Class<V> valueClz) {
        PublishSubject<ConsumerRecord<K, V>> records = PublishSubject.create();

        JavaConsumersBuilder.newBuilder(keyClz, valueClz)
                .clientId(topic + "-consumer-" + random.nextInt())
                .bootstrapServers(kafkaUnit.bootstrapServers())
                .groupId(topic + "-consumer-group-" + random.nextInt())
                .subscribe(new String[] { topic })
                .concurrency(1)
                .javaRecordListener(records::onNext)
                .build().start();

        return records;
    }
}
