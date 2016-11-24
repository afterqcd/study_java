package com.afterqcd.study.kafka

import com.afterqcd.study.kafka.unit.KafkaUnit
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/11/22.
  */
trait KafkaIntegrationTest extends FlatSpec with Matchers with BeforeAndAfterAll {
  var kafkaUnit: KafkaUnit = _
  val brokers = 1

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    kafkaUnit = new KafkaUnit
    kafkaUnit.setUpBrokers(brokers)
  }

  override protected def afterAll(): Unit = {
    kafkaUnit.shutdown()
    kafkaUnit = null
    super.afterAll()
  }

  def consumeToEnd[K, V](consumer: KafkaConsumer[K, V]): ConsumerRecords[K, V] = {
    val consumerRecordsList = Stream.continually(consumer.poll(1000))
      .dropWhile(_.count() == 0).takeWhile(_.count() > 0)

    val mergedConsumerRecords = consumerRecordsList.flatMap(_.iterator().asScala)
      .groupBy(r => new TopicPartition(r.topic(), r.partition()))
      .mapValues(_.asJava).asJava

    new ConsumerRecords[K, V](mergedConsumerRecords)
  }
}
