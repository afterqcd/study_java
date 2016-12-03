package com.afterqcd.study.kafka.clients.consumer

import java.util.Properties

import com.afterqcd.study.kafka.clients.{Builder, DeliverySemantics}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}

import scala.collection.JavaConverters._
import scala.reflect.ClassTag

/**
  * Created by afterqcd on 2016/12/1.
  */
class ConsumersBuilder[K, V](val props: Properties, val keyClz: Class[K], val valueClz: Class[V])
  extends Builder[ConsumersBuilder[K, V]] {

  private var topics: Option[Seq[String]] = None
  private var concurrency: Option[Int] = None
  private var recordListener: Option[ConsumerRecord[K, V] => Unit] = None
  private var recordBatchListener: Option[Seq[ConsumerRecord[K, V]] => Unit] = None

  prop(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  /**
    * Set consumer group id.
    * @param groupId
    * @return
    */
  def groupId(groupId: String): ConsumersBuilder[K, V] = {
    prop(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    this
  }

  /**
    * Set auto offset reset.
    * @param autoOffsetReset
    * @return
    */
  def autoOffsetReset(autoOffsetReset: String): ConsumersBuilder[K, V] = {
    prop(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
    this
  }

  /**
    * Set topics to subscribe.
    * @param topics
    * @return
    */
  def subscribe(topics: Seq[String]): ConsumersBuilder[K, V] = {
    this.topics = Some(topics)
    this
  }

  /**
    * Set consumer concurrency.
    * @param concurrency
    * @return
    */
  def concurrency(concurrency: Int): ConsumersBuilder[K, V] = {
    this.concurrency = Some(concurrency)
    this
  }

  /**
    * Set record listener.
    * @param listener
    * @return
    */
  def recordListener(listener: ConsumerRecord[K, V] => Unit): ConsumersBuilder[K, V] = {
    if (recordBatchListener.isDefined)
      throw new IllegalArgumentException(s"batchRecordListener has already been configured")

    recordListener = Some(listener)
    this
  }

  /**
    * Set record batch listener.
    * @param listener
    * @return
    */
  def recordBatchListener(listener: Seq[ConsumerRecord[K, V]] => Unit): ConsumersBuilder[K, V] = {
    if (recordListener.isDefined)
      throw new IllegalArgumentException(s"recordListener has already been configured")

    recordBatchListener = Some(listener)
    this
  }

  /**
    * Build consumers.
    * @return
    */
  def build(): IConsumers[K, V] = {
    if (!topics.exists(_.nonEmpty))
      throw new IllegalArgumentException(s"No topic or topic is empty")

    props.put("topics", topics.get.mkString(","))

    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
    props.put("delivery.semantics", deliverySemantics.getOrElse(DeliverySemantics.AtLeastOnce))

    props.putAll(deserializerProps.asJava)

    new DefaultConsumers[K, V](props, concurrency.getOrElse(1), listener)
  }

  private def deserializerProps: Map[String, String] = {
    Map(
      "key.class" -> keyClz.getName,
      "value.class" -> valueClz.getName,

      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> Deserializers.deserializerFor(keyClz),
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> Deserializers.deserializerFor(valueClz)
    )
  }

  private def listener: Seq[ConsumerRecord[K, V]] => Unit = {
    if (Seq(recordListener, recordBatchListener).forall(_.isEmpty))
      throw new IllegalArgumentException("No record listener is configured")

    recordBatchListener.getOrElse {
      records => records.foreach(recordListener.get)
    }
  }
}

object ConsumersBuilder {
  /**
    * Create new Consumers builder.
    * @param props
    * @param keyTag
    * @param valueTag
    * @tparam K
    * @tparam V
    * @return
    */
  def apply[K, V](props: Properties = new Properties())
                 (implicit keyTag: ClassTag[K], valueTag: ClassTag[V]): ConsumersBuilder[K, V] = {
    val keyClz = keyTag.runtimeClass.asInstanceOf[Class[K]]
    val valueClz = valueTag.runtimeClass.asInstanceOf[Class[V]]
    new ConsumersBuilder[K, V](props, keyClz, valueClz)
  }
}
