package com.afterqcd.study.kafka.consumer

import java.util.concurrent.CountDownLatch

import com.afterqcd.study.kafka.builder.{ConsumersBuilder, ProducerBuilder}
import com.afterqcd.study.kafka.model.LogEntryOuterClass
import com.afterqcd.study.kafka.model.LogEntryOuterClass.LogEntry
import com.afterqcd.study.kafka.{DeliverySemantics, KafkaIntegrationTest}
import org.apache.kafka.clients.consumer.ConsumerRecord

import scala.collection.mutable.ArrayBuffer

/**
  * Created by afterqcd on 2016/12/1.
  */
class ConsumersTest extends KafkaIntegrationTest {
  "DefaultConsumers" should "消费合法的Protobuf消息" in {
    kafkaUnit.createTopic("test", 1, 1)

    val deliverySemantics = DeliverySemantics.AtMostOnce

    val producer = ProducerBuilder[String, LogEntryOuterClass.LogEntry]()
      .clientId("test-producer")
      .bootstrapServers(kafkaUnit.bootstrapServers)
      .messageDeliverySemantics(deliverySemantics)
      .defaultTopic("test")
      .build()

    val latch = new CountDownLatch(2)
    val records = ArrayBuffer.empty[ConsumerRecord[String, LogEntryOuterClass.LogEntry]]

    val consumers = ConsumersBuilder[String, LogEntry]()
      .clientId("test_consumer")
      .bootstrapServers(kafkaUnit.bootstrapServers)
      .messageDeliverySemantics(deliverySemantics)
      .groupId("test")
      .subscribe(Seq("test"))
      .concurrency(1)
      .recordListener(new IRecordListener[String, LogEntry] {
        override def onRecord(record: ConsumerRecord[String, LogEntry]): Unit = {
          records += record
          latch.countDown()
        }
      }).build()
    consumers.start()

    val logEntryA = LogEntryOuterClass.LogEntry.newBuilder().setIp("127.0.0.1").build()
    val logEntryB = LogEntryOuterClass.LogEntry.newBuilder().setIp("localhost").build()

    producer.sendDefault("a", logEntryA)
    producer.sendDefault("b", logEntryB)
    producer.flush()

    latch.await()

    records.map(r => (r.key(), r.value())) should contain theSameElementsInOrderAs Seq(
      ("a", logEntryA),
      ("b", logEntryB)
    )
  }
}
