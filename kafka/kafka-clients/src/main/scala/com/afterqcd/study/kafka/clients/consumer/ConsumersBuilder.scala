package com.afterqcd.study.kafka.clients.consumer

import java.util.Properties

import com.afterqcd.study.kafka.clients.DeliverySemantics
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}

import scala.collection.JavaConverters._
import scala.reflect.ClassTag

/**
  * Created by afterqcd on 2016/12/1.
  */
class ConsumersBuilder[K, V](val props: Properties, val keyClz: Class[K], val valueClz: Class[V]) {

  private var topics: Option[Array[String]] = None
  private var concurrency: Option[Int] = None
  private var recordListener: Option[ConsumerRecord[K, V] => Unit] = None
  private var recordBatchListener: Option[Seq[ConsumerRecord[K, V]] => Unit] = None
  protected var deliverySemantics = DeliverySemantics.AtLeastOnce

  prop(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  /**
    * Add property for producer.
    * @param name
    * @param value
    * @return
    */
  def prop(name: String, value: String): ConsumersBuilder[K, V] = {
    props.put(name, value)
    this
  }

  /**
    * Set client id.
    * @param clientId
    * @return
    */
  def clientId(clientId: String): ConsumersBuilder[K, V] = {
    prop(ConsumerConfig.CLIENT_ID_CONFIG, clientId)
    this
  }

  /**
    * Set bootstrap servers.
    * @param bootstrapServers
    * @return
    */
  def bootstrapServers(bootstrapServers: String): ConsumersBuilder[K, V] = {
    prop(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    this
  }

  /**
    * Set message delivery semantics.
    * DeliverySemantics.AtMostOnce: you can get quickest performance with possible lost
    * DeliverySemantics.AtLeastOnce: you can get strongest durability with reduced performance
    * @param deliverySemantics
    * @return
    */
  def messageDeliverySemantics(deliverySemantics: String): ConsumersBuilder[K, V] = {
    this.deliverySemantics = deliverySemantics
    this
  }

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
  def subscribe(topics: Array[String]): ConsumersBuilder[K, V] = {
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

  def javaRecordListener(listener: IRecordListener[K, V]): ConsumersBuilder[K, V] = {
    recordListener(listener.onRecord)
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

  def javaRecordBatchListener(listener: IRecordBatchListener[K, V]): ConsumersBuilder[K, V] = {
    recordBatchListener(rs => listener.onRecords(rs.toArray))
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
    props.put("delivery.semantics", deliverySemantics)

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

  def apply[K, V](props: Properties, keyClz: Class[K], valueClz: Class[V])
  : ConsumersBuilder[K, V] = {
    new ConsumersBuilder[K, V](props, keyClz, valueClz)
  }
}
