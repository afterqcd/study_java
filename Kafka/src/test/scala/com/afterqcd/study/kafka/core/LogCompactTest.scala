package com.afterqcd.study.kafka.core

import java.util
import java.util.Properties

import com.afterqcd.study.kafka.KafkaIntegrationTest
import kafka.log.LogConfig
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

/**
  * Created by afterqcd on 2016/11/24.
  */
class LogCompactTest extends KafkaIntegrationTest {
  "Kafka" should "普通Topic保留最新一段时间的所有日志" in {
    kafkaUnit.createTopic("normal", 1, 1)

    val producer = createProducer("normal")
    val consumer = createConsumer(scene = "normal", group = "group", topic = "normal")

    producer.send(new ProducerRecord("normal", "a", "1"))
    producer.send(new ProducerRecord("normal", "b", "2"))
    producer.send(new ProducerRecord("normal", "a", "3"))
    producer.send(new ProducerRecord("normal", "b", "4"))
    producer.flush()

    keyValueRecords(consumeToEnd(consumer)) should contain theSameElementsInOrderAs Seq (
      ("a", "1"), ("b", "2"), ("a", "3"), ("b", "4")
    )

    producer.close()
    consumer.close()
  }

  /**
    * Log Compact的相关限制
    * * 不会处理活跃的Segment（最后那个），以免影响producer发送消息的性能
    * * 默认只有一个线程在做清理压缩的工作，极有可能会挂掉
    */
  it should "Compact Topic仅为每个Key保留最新的Value" in {
    val props = new Properties()
    props.put(LogConfig.CleanupPolicyProp, LogConfig.Compact)
    // 50ms产生一个segment，让存入消息的Segment迅速成为非活跃segment
    props.put(LogConfig.SegmentMsProp, "50")

    kafkaUnit.createTopic("compact", 1, 1, props)

    val producer = createProducer("compact")
    producer.send(new ProducerRecord("compact", "a", "1"))
    producer.send(new ProducerRecord("compact", "b", "2"))
    producer.send(new ProducerRecord("compact", "c", "C"))
    producer.send(new ProducerRecord("compact", "a", "3"))
    producer.send(new ProducerRecord("compact", "b", "4"))
    producer.send(new ProducerRecord("compact", "c", null))
    producer.flush()

    val consumer1 = createConsumer(scene = "compact", group = "group1", topic = "compact")
    keyValueRecords(consumeToEnd(consumer1)) should contain theSameElementsInOrderAs Seq(
      ("a", "1"), ("b", "2"), ("c", "C"), ("a", "3"), ("b", "4"), ("c", null)
    )

    // 促使日志合并
    Thread.sleep(150)
    // 如果没有新的写入，实际上并不会真的产生新的segment
    producer.send(new ProducerRecord("compact", "d", "D"))
    producer.flush()

    val consumer2 = createConsumer(scene = "compact", group = "group2", topic = "compact")
    keyValueRecords(consumeToEnd(consumer2)) should contain theSameElementsInOrderAs Seq(
      ("a", "3"), ("b", "4"), ("c", null), ("d", "D")
    )

    producer.close()
    consumer2.close()
  }

  private def createProducer(scene: String): KafkaProducer[String, String] = {
    val props = new Properties()
    props.put(ProducerConfig.CLIENT_ID_CONFIG, s"${scene}_producer")
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers)
    props.put(ProducerConfig.ACKS_CONFIG, "all")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }

  private def createConsumer(scene: String, group: String, topic: String): KafkaConsumer[String, String] = {
    val props = new Properties()
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, s"${scene}_consumer")
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUnit.bootstrapServers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, s"${scene}_$group")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList(topic))
    consumer
  }
}
