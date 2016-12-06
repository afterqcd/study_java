package com.afterqcd.study.kafka.test

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/12/6.
  */
object ConsumerUtils {
  def consumeToEnd[K, V](consumer: KafkaConsumer[K, V]): ConsumerRecords[K, V] = {
    val consumerRecordsList = Stream.continually(consumer.poll(1000))
      .dropWhile(_.count() == 0).takeWhile(_.count() > 0)

    val mergedConsumerRecords = consumerRecordsList.flatMap(_.iterator().asScala)
      .groupBy(r => new TopicPartition(r.topic(), r.partition()))
      .mapValues(_.asJava).asJava

    new ConsumerRecords[K, V](mergedConsumerRecords)
  }
}
