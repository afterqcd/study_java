package com.afterqcd.study.kafka.producer

import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.{Metric, MetricName}
import rx.Observable

/**
  * Created by afterqcd on 2016/11/29.
  */
trait IProducer[K, V] {
  /**
    * Send data to default topic.
    * @param data
    * @return
    */
  def sendDefault(data: V): Observable[RecordMetadata]

  /**
    * Send key and data to default topic.
    * @param key
    * @param data
    * @return
    */
  def sendDefault(key: K, data: V): Observable[RecordMetadata]

  /**
    * Send data to specified topic.
    * @param topic
    * @param data
    * @return
    */
  def send(topic: String, data: V): Observable[RecordMetadata]

  /**
    * Send key and data to specified topic.
    * @param topic
    * @param key
    * @param data
    * @return
    */
  def send(topic: String, key: K, data: V): Observable[RecordMetadata]

  /**
    * Flush the producer.
    */
  def flush(): Unit

  /**
    * Get the full set of internal metrics maintained by the producer.
    * @return
    */
  def metrics: Map[MetricName, _ <: Metric]

  /**
    * Close the producer.
    */
  def close(): Unit
}
