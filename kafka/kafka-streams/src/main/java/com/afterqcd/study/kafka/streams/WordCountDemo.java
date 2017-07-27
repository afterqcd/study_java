package com.afterqcd.study.kafka.streams;

import com.afterqcd.study.kafka.serde.UnifySerdes;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class WordCountDemo {
    private static final String TextLinesTopics = "text-lines";
    private static final String WordCountsTopics = "word-counts";
    private static final String AppPropertiesResources = "application.properties";
    private static final String WordSplitter = " ";
    private static final String CountsStore = "counts";

    public static void main(String[] args) {
        System.out.println("Word Count in Kafka Streams");

        Serde<String> stringSerde = UnifySerdes.serde(String.class);
        Serde<Long> longSerde = UnifySerdes.serde(Long.class);

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> textLines = builder.stream(stringSerde, stringSerde, TextLinesTopics);
        KStream<String, Long> wordCounts = textLines.flatMapValues(line -> Arrays.asList(line.toLowerCase().split(WordSplitter)))
                .groupBy((key, word) -> word, stringSerde, stringSerde)
                .count(CountsStore)
                .toStream();
        wordCounts.to(stringSerde, longSerde, WordCountsTopics);

        KafkaStreams kafkaStreams = new KafkaStreams(builder, streamsProps());
        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

    private static Properties streamsProps() {
        Config config = ConfigFactory.parseResources(AppPropertiesResources);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-lambda-example");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.getString("kafka.bootstrap.servers"));
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, config.getString("zookeeper.connect"));
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
        return props;
    }
}
