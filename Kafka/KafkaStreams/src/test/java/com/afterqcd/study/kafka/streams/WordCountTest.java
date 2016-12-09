package com.afterqcd.study.kafka.streams;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import com.afterqcd.study.kafka.StreamsIntegration;
import com.afterqcd.study.kafka.clients.producer.IProducer;
import com.afterqcd.study.kafka.test.KeyValueRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.junit.Test;
import rx.Observable;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/3.
 */
public class WordCountTest extends StreamsIntegration {
    private static final String TextLinesTopic = "text-lines-topic";
    private static final String WordCountsTopic = "word-counts-topic";

    @Test
    public void testStreamingWordCount() throws Exception {
        kafkaUnit.createTopic(TextLinesTopic, 3, 1);
        kafkaUnit.createCompactTopic(WordCountsTopic, 3, 1);

        KafkaStreams streams = streams();
        streams.cleanUp(); // do not use in real app
        streams.start();

        IProducer<String, String> producer
                = producer(TextLinesTopic, String.class, String.class);
        producer.sendDefault("kafka topics");
        producer.sendDefault("kafka unit test");
        producer.sendDefault("test kafka");
        producer.flush();

        Observable<ConsumerRecord<String, Long>> wordCounts
                = records(WordCountsTopic, String.class, Long.class);
        assertThat(
                keyValueRecords(wordCounts, 4),
                hasItems(
                        new KeyValueRecord<>("kafka", 3L), new KeyValueRecord<>("topics", 1L),
                        new KeyValueRecord<>("unit", 1L), new KeyValueRecord<>("test", 2L)
                )
        );

        streams.close();
    }

    private KafkaStreams streams() {
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        final KStreamBuilder builder = new KStreamBuilder();

        final KStream<String, String> textLines = builder.stream(stringSerde, stringSerde, TextLinesTopic);

        final KStream<String, Long> wordCounts = textLines
                .flatMapValues(line -> Arrays.asList(line.toLowerCase().split(" ")))
                .groupBy((key, word) -> word, Serdes.String(), Serdes.String())
                .count("counts")
                .toStream();

        wordCounts.to(stringSerde, longSerde, WordCountsTopic);

        Properties props = new Properties();
//        props.put(StreamsConfig.APPLICATION_SERVER_CONFIG, "localhost:50000");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-lambda-example");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers());
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, kafkaUnit.zkUnit().zkConnect());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 2 * 1000);

        return new KafkaStreams(builder, props);
    }

}
