package com.afterqcd.study.kafka.clients.producer

import java.util
import java.util.Properties

import com.afterqcd.study.kafka.clients.DeliverySemantics
import com.afterqcd.study.kafka.model.LogEntryOuterClass
import com.afterqcd.study.kafka.test.{Integration, KeyValueRecord}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

/**
  * Created by afterqcd on 2016/11/30.
  */
class ProducerTest extends Integration {
  "DefaultProducer" should "生成合法Protobuf的消息" in {
    kafkaUnit.createTopic("test", 3, 1)

    val producer = ProducerBuilder[String, LogEntryOuterClass.LogEntry]()
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

    allKeyValueRecords(consumer) should contain theSameElementsInOrderAs Seq(
      KeyValueRecord("a", logEntryA),
      KeyValueRecord("b", logEntryB)
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
