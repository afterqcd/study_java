package com.afterqcd.study.kafka.test

import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import rx.lang.scala.Observable

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/11/22.
  */
trait Integration extends FlatSpec with Matchers with BeforeAndAfterAll {
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

  /**
    * Consume consumer to end and convert ConsumerRecord to KeyValueRecord.
    * @param consumer
    * @tparam K
    * @tparam V
    * @return
    */
  def allKeyValueRecords[K, V](consumer: KafkaConsumer[K, V]): Seq[KeyValueRecord[K, V]] = {
    ConsumerUtils.consumeToEnd(consumer).asScala.toSeq.map(r => new KeyValueRecord(r))
  }

  /**
    * Take records by count.
    * @param records
    * @param count
    * @tparam K
    * @tparam V
    * @return
    */
  def keyValueRecords[K, V](records: Observable[ConsumerRecord[K, V]], count: Int): Seq[KeyValueRecord[K, V]] = {
    records.take(count).map(r => new KeyValueRecord[K, V](r)).toList.toBlocking.first
  }
}
