package com.afterqcd.study.kafka.core

import java.util.Properties

import com.afterqcd.study.kafka.KafkaIntegrationTest
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/11/22.
  */
class ProducerConsumerTest extends KafkaIntegrationTest {
  "Kafka" should "生成并消费消息" in {
    kafkaUnit.createTopic("test", 3, 1)

    val producerProps = new Properties()
    producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, "test_producer")
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers)
    producerProps.put(ProducerConfig.ACKS_CONFIG, "all")
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](producerProps)

    val consumerProps = new Properties()
    consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "test_consumer")
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers)
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test")
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    val consumer = new KafkaConsumer[String, String](consumerProps)
    consumer.subscribe(Seq("test").asJavaCollection)

    producer.send(new ProducerRecord[String, String]("test", "a", "A"))
    producer.send(new ProducerRecord[String, String]("test", "b", "B"))
    producer.flush()

    val records = consumeToEnd(consumer)
    records.count() should be (2)
    val iterator = records.iterator()
    val messageA = iterator.next()
    messageA.key() should be ("a")
    messageA.value() should be ("A")
    val messageB = iterator.next()
    messageB.key() should be ("b")
    messageB.value() should be ("B")

    producer.close()
    consumer.close()
  }
}
