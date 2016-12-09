package com.afterqcd.study.kafka.streams;

import static com.afterqcd.study.kafka.serde.UnifySerdes.serdeFrom;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import com.afterqcd.study.kafka.StreamsIntegration;
import com.afterqcd.study.kafka.clients.producer.IProducer;
import com.afterqcd.study.kafka.model.CustomerOuterClass.Customer;
import com.afterqcd.study.kafka.test.KeyValueRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.junit.Test;
import rx.Observable;

import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class CustomerStatisticsTest extends StreamsIntegration {
    private static final String CustomersTopic = "customers";
    private static final String AgeStatisticsTopic = "age-statistics";

    @Test
    public void testStatCustomers() throws Exception {
        kafkaUnit.createTopic(CustomersTopic, 3, 1);
        kafkaUnit.createCompactTopic(AgeStatisticsTopic, 1, 1);

        KafkaStreams streams = streams();
        streams.cleanUp();
        streams.start();

        IProducer<String, Customer> producer
                = producer(CustomersTopic, String.class, Customer.class);
        producer.sendDefault(Customer.newBuilder().setAge(15).build());
        producer.sendDefault(Customer.newBuilder().setAge(20).build());
        producer.sendDefault(Customer.newBuilder().setAge(25).build());
        producer.sendDefault(Customer.newBuilder().setAge(15).build());
        producer.sendDefault(Customer.newBuilder().setAge(20).build());
        producer.flush();

        Observable<ConsumerRecord<Integer, Long>> ageStatistics
                = records(AgeStatisticsTopic, Integer.class, Long.class);
        assertThat(keyValueRecords(ageStatistics, 3), hasItems(
                new KeyValueRecord<>(15, 2L), new KeyValueRecord<>(20, 2L),
                new KeyValueRecord<>(25, 1L)
        ));

        streams.close();
    }

    private KafkaStreams streams() {
        KStreamBuilder builder = new KStreamBuilder();

        KStream<String, Customer> customers =
                builder.stream(serdeFrom(String.class), serdeFrom(Customer.class), CustomersTopic);
        KStream<Integer, Long> ageCounts =
                customers.map((k, c) -> KeyValue.pair(c.getAge(), 1))
                        .groupByKey(serdeFrom(Integer.class), serdeFrom(Integer.class))
                        .count("age-counts")
                        .toStream();

        ageCounts.to(serdeFrom(Integer.class), serdeFrom(Long.class), AgeStatisticsTopic);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "customer-statistics-stream-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers());
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, kafkaUnit.zkUnit().zkConnect());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 2 * 1000);

        return new KafkaStreams(builder, props);
    }

}
