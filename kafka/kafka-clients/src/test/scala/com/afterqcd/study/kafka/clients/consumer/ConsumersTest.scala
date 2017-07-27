package com.afterqcd.study.kafka.clients.consumer

import com.afterqcd.study.kafka.clients.DeliverySemantics
import com.afterqcd.study.kafka.clients.producer.ProducerBuilder
import com.afterqcd.study.kafka.model.LogEntryOuterClass
import com.afterqcd.study.kafka.model.LogEntryOuterClass.LogEntry
import com.afterqcd.study.kafka.test.{Integration, KeyValueRecord}
import org.apache.kafka.clients.consumer.ConsumerRecord
import rx.lang.scala.subjects.PublishSubject

/**
  * Created by afterqcd on 2016/12/1.
  */
class ConsumersTest extends Integration {
  "DefaultConsumers" should "消费合法的Protobuf消息" in {
    kafkaUnit.createTopic("test", 1, 1)

    val deliverySemantics = DeliverySemantics.AtMostOnce

    val producer = ProducerBuilder[String, LogEntryOuterClass.LogEntry]()
      .clientId("test-producer")
      .bootstrapServers(kafkaUnit.bootstrapServers)
      .messageDeliverySemantics(deliverySemantics)
      .defaultTopic("test")
      .build()

    val records = PublishSubject[ConsumerRecord[String, LogEntryOuterClass.LogEntry]]

    ConsumersBuilder[String, LogEntry]()
      .clientId("test_consumer")
      .bootstrapServers(kafkaUnit.bootstrapServers)
      .messageDeliverySemantics(deliverySemantics)
      .groupId("test")
      .subscribe(Array("test"))
      .concurrency(1)
      //      .recordBatchListener(rs => rs.foreach(records.onNext))
      .recordListener(records.onNext)
      .build().start()

    val logEntryA = LogEntryOuterClass.LogEntry.newBuilder().setIp("127.0.0.1").build()
    val logEntryB = LogEntryOuterClass.LogEntry.newBuilder().setIp("localhost").build()

    producer.sendDefault("a", logEntryA)
    producer.sendDefault("b", logEntryB)
    producer.flush()

    keyValueRecords(records, 2) should contain theSameElementsInOrderAs Seq(
      KeyValueRecord("a", logEntryA),
      KeyValueRecord("b", logEntryB)
    )
  }
}
