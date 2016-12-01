package com.afterqcd.study.kafka.producer

import java.util
import java.util.Properties

import com.afterqcd.study.kafka.model.LogEntryOuterClass
import com.afterqcd.study.kafka.{DeliverySemantics, KafkaIntegrationTest}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

/**
  * Created by afterqcd on 2016/11/30.
  */
class ProducerTest extends KafkaIntegrationTest {
  "DefaultProducer" should "生成合法Protobuf的消息" in {
    kafkaUnit.createTopic("test", 3, 1)

    val producer = ProducerBuilder.newBuilder[String, LogEntryOuterClass.LogEntry]
      .clientId("test-producer")
      .bootstrapServers(kafkaUnit.bootstrapServers)
      .messageDeliverySemantics(DeliverySemantics.AtLeastOnce)
      .defaultTopic("test")
      .build()

    val consumer = createConsumer

    val logEntryA = LogEntryOuterClass.LogEntry.newBuilder().setIp("127.0.0.1").build()
    val logEntryB = LogEntryOuterClass.LogEntry.newBuilder().setIp("localhost").build()

    producer.sendDefault("a", logEntryA)
    producer.sendDefault("b", logEntryB)
    producer.flush()

    keyValueRecords(consumeToEnd(consumer)) should contain theSameElementsInOrderAs Seq(
      ("a", logEntryA),
      ("b", logEntryB)
    )

    consumer.close()
  }

  private def createConsumer: KafkaConsumer[String, LogEntryOuterClass.LogEntry] = {
    val props = new Properties()
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "test_consumer")
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "test")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.afterqcd.study.kafka.protobuf.ProtobufDeserializer")
    props.put("value.class", "com.afterqcd.study.kafka.model.LogEntryOuterClass$LogEntry")
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    val consumer = new KafkaConsumer[String, LogEntryOuterClass.LogEntry](props)
    consumer.subscribe(util.Arrays.asList("test"))
    consumer
  }
}
