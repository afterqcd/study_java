package com.afterqcd.study.kafka.producer

import java.util.Properties

import com.afterqcd.study.kafka.util.CloseOnExit
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.{Metric, MetricName}
import rx.Observable

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/11/29.
  */
class DefaultProducer[K, V](val props: Properties, defaultTopic: Option[String])
  extends IProducer[K, V] with CloseOnExit {

  private val kafkaProducer = new KafkaProducer[K, V](props)

  /**
    * Send data to default topic.
    *
    * @param data
    * @return
    */
  override def sendDefault(data: V): Observable[RecordMetadata] = {
    checkDefaultTopic()
    send(new ProducerRecord[K, V](defaultTopic.get, data))
  }

  private def checkDefaultTopic(): Unit = {
    assert(defaultTopic.isDefined, "Send data to default topic, but default topic is not defined")
  }

  private def send(record: ProducerRecord[K, V]): Observable[RecordMetadata] = {
    Observable.from(kafkaProducer.send(record))
  }

  /**
    * Send key and data to default topic.
    *
    * @param key
    * @param data
    * @return
    */
  override def sendDefault(key: K, data: V): Observable[RecordMetadata] = {
    checkDefaultTopic()
    send(new ProducerRecord[K, V](defaultTopic.get, key, data))
  }

  /**
    * Send data to specified topic.
    *
    * @param topic
    * @param data
    * @return
    */
  override def send(topic: String, data: V): Observable[RecordMetadata] = {
    send(new ProducerRecord[K, V](topic, data))
  }

  /**
    * Send key and data to specified topic.
    *
    * @param topic
    * @param key
    * @param data
    * @return
    */
  override def send(topic: String, key: K, data: V): Observable[RecordMetadata] = {
    send(new ProducerRecord[K, V](topic, key, data))
  }

  /**
    * Flush the producer.
    */
  override def flush(): Unit = {
    kafkaProducer.flush()
  }

  /**
    * Get the full set of internal metrics maintained by the producer.
    *
    * @return
    */
  override def metrics: Map[MetricName, _ <: Metric] = {
    kafkaProducer.metrics().asScala.toMap
  }

  override protected def doClose(): Unit = {
    kafkaProducer.close()
    println("closing")
  }
}
