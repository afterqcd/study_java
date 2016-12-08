package com.afterqcd.study.kafka.streams;

import static com.afterqcd.study.kafka.serde.UnifySerdes.serdeFrom;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import com.afterqcd.study.kafka.clients.DeliverySemantics$;
import com.afterqcd.study.kafka.clients.consumer.JavaConsumersBuilder;
import com.afterqcd.study.kafka.clients.producer.IProducer;
import com.afterqcd.study.kafka.clients.producer.JavaProducerBuilder;
import com.afterqcd.study.kafka.model.CustomerOuterClass.Customer;
import com.afterqcd.study.kafka.test.JavaIntegration;
import com.afterqcd.study.kafka.test.KeyValueRecord;
import kafka.log.LogConfig;
import kafka.server.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.junit.Test;
import rx.subjects.PublishSubject;

import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class CustomerStatisticsDemo extends JavaIntegration {
    private static final String CustomersTopic = "customers";
    private static final String AgeStatisticsTopic = "age-statistics";

    @Test
    public void testStatCustomers() throws Exception {
        kafkaUnit.createTopic(CustomersTopic, 1, 1, new Properties());
        Properties props = new Properties();
        props.put(LogConfig.CleanupPolicyProp(), "compact");
        kafkaUnit.createTopic(AgeStatisticsTopic, 1, 1, props);

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, Customer> customers = builder.stream(
                serdeFrom(String.class), serdeFrom(Customer.class),
                CustomersTopic
        );
        KStream<Integer, Long> ageCounts = customers.map((k, c) -> KeyValue.pair(c.getAge(), 1))
                .groupByKey(serdeFrom(Integer.class), serdeFrom(Integer.class))
                .count("age-counts")
                .toStream();

        ageCounts.to(serdeFrom(Integer.class), serdeFrom(Long.class), AgeStatisticsTopic);

        KafkaStreams streams = new KafkaStreams(builder, streamsProps());
        streams.start();

        IProducer<String, Customer> customersProducer =
                JavaProducerBuilder.newBuilder(String.class, Customer.class)
                        .clientId("customers-producer")
                        .bootstrapServers(kafkaUnit.bootstrapServers())
                        .defaultTopic(CustomersTopic)
                        .messageDeliverySemantics(DeliverySemantics$.MODULE$.AtLeastOnce())
                        .build();
        customersProducer.sendDefault(Customer.newBuilder().setAge(15).build());
        customersProducer.sendDefault(Customer.newBuilder().setAge(20).build());
        customersProducer.sendDefault(Customer.newBuilder().setAge(25).build());
        customersProducer.sendDefault(Customer.newBuilder().setAge(15).build());
        customersProducer.sendDefault(Customer.newBuilder().setAge(20).build());
        customersProducer.flush();

        PublishSubject<ConsumerRecord<Integer, Long>> subject = PublishSubject.create();

        JavaConsumersBuilder.newBuilder(Integer.class, Long.class)
                .clientId("age-statistics-consumer")
                .bootstrapServers(kafkaUnit.bootstrapServers())
                .messageDeliverySemantics(DeliverySemantics$.MODULE$.AtLeastOnce())
                .groupId("collector")
                .subscribe(new String[] { AgeStatisticsTopic })
                .concurrency(1)
                .javaRecordListener(subject::onNext)
                .build().start();

        assertThat(keyValueRecords(subject, 3), hasItems(
                new KeyValueRecord<>(15, 2L), new KeyValueRecord<>(20, 2L),
                new KeyValueRecord<>(25, 1L)
        ));

        streams.close();
    }

    private Properties streamsProps() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "customer-statistics-stream-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers());
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, kafkaUnit.zkUnit().zkConnect());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 2 * 1000);

        return props;
    }
}
