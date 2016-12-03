package com.afterqcd.study.kafka.clients.core

import java.util
import java.util.Properties

import com.afterqcd.study.kafka.KafkaIntegrationTest
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

/**
  * Created by afterqcd on 2016/11/22.
  */
class ProducerConsumerTest extends KafkaIntegrationTest {
  "Kafka" should "生成并消费消息" in {
    kafkaUnit.createTopic("test", 3, 1)

    val producer = createProducer
    val consumer = createConsumer

    producer.send(new ProducerRecord[String, String]("test", "a", "A"))
    producer.send(new ProducerRecord[String, String]("test", "b", "B"))
    producer.flush()

    keyValueRecords(consumeToEnd(consumer)) should contain theSameElementsInOrderAs Seq(
      ("a", "A"), ("b", "B")
    )

    producer.close()
    consumer.close()
  }

  private def createProducer: KafkaProducer[String, String] = {
    val props = new Properties()
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "test_producer")
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers)
    props.put(ProducerConfig.ACKS_CONFIG, "all")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }

  private def createConsumer: KafkaConsumer[String, String] = {
    val props = new Properties()
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "test_consumer")
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "test")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList("test"))
    consumer
  }
}
