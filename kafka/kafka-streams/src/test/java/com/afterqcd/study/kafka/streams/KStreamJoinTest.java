package com.afterqcd.study.kafka.streams;

import com.afterqcd.study.kafka.StreamsIntegration;
import com.afterqcd.study.kafka.clients.producer.IProducer;
import com.afterqcd.study.kafka.serde.UnifySerdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.junit.Test;
import rx.subjects.PublishSubject;

import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/9.
 */
public class KStreamJoinTest extends StreamsIntegration {
    private static final String TOPIC_A = "a";
    private static final String TOPIC_B = "b";

    @Test
    public void shouldJoin() throws Exception {
        kafkaUnit.createTopic(TOPIC_A, 1, 1);
        kafkaUnit.createTopic(TOPIC_B, 1, 1);

        Serde<String> stringSerde = UnifySerdes.serde(String.class);
        Serde<Integer> integerSerde = UnifySerdes.serde(Integer.class);

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> a = builder.stream(stringSerde, stringSerde, TOPIC_A);
        KStream<String, Integer> b = builder.stream(stringSerde, integerSerde, TOPIC_B);
        KStream<String, String> ab = a.join(
                b, (s, i) -> {
                    System.out.println("join " + s + " " + i);
                    return s + i;
                }, JoinWindows.of(1000), stringSerde, stringSerde, integerSerde
        );

        PublishSubject<KeyValue<String, String>> results = PublishSubject.create();
        ab.foreach(KeyValue::pair);

        KafkaStreams streams = new KafkaStreams(builder, streamsProps());
        streams.cleanUp();
        streams.start();

        IProducer<String, String> aProducer = producer(TOPIC_A, String.class, String.class);
        aProducer.sendDefault("a", "A");
        aProducer.sendDefault("b", "B");
        aProducer.sendDefault("c", "C");
        aProducer.flush();

        IProducer<String, Integer> bProducer = producer(TOPIC_B, String.class, Integer.class);
        bProducer.sendDefault("a", 1);
        bProducer.sendDefault("b", 2);
        bProducer.flush();

        results.take(2).toList().toBlocking().first().forEach(System.out::println);

        streams.close();
    }

    private Properties streamsProps() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kstream-join-test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers());
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, kafkaUnit.zkUnit().zkConnect());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 50);
        return props;
    }
}
