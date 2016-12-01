package com.afterqcd.study.kafka.producer

import java.util.Properties

import com.afterqcd.study.kafka.DeliverySemantics
import org.apache.kafka.clients.producer.ProducerConfig

import scala.collection.JavaConverters._
import scala.reflect.ClassTag

/**
  * Created by afterqcd on 2016/11/29.
  */
class ProducerBuilder[K, V](val props: Properties, val keyClz: Class[K], val valueClz: Class[V]) {
  private var deliverySemantics: Option[String] = None
  private var defaultTopic: Option[String] = None

  /**
    * Add property for producer.
    * @param name
    * @param value
    * @return
    */
  def prop(name: String, value: String): ProducerBuilder[K, V] = {
    props.put(name, value)
    this
  }

  /**
    * Set client id.
    * @param clientId
    * @return
    */
  def clientId(clientId: String): ProducerBuilder[K, V] = {
    prop(ProducerConfig.CLIENT_ID_CONFIG, clientId)
    this
  }

  /**
    * Set bootstrap servers.
    * @param bootstrapServers
    * @return
    */
  def bootstrapServers(bootstrapServers: String): ProducerBuilder[K, V] = {
    prop(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    this
  }

  /**
    * Set message delivery semantics.
    * DeliverySemantics.AtMostOnce: you can get quickest performance with possible lost
    * DeliverySemantics.AtLeastOnce: you can get strongest durability with reduced performance
    * @param deliverySemantics
    * @return
    */
  def messageDeliverySemantics(deliverySemantics: String): ProducerBuilder[K, V] = {
    this.deliverySemantics = Some(deliverySemantics)
    this
  }

  /**
    * Set default topic send messages to.
    * @param topic
    * @return
    */
  def defaultTopic(topic: String): ProducerBuilder[K, V] = {
    this.defaultTopic = Some(topic)
    this
  }

  /**
    * Build kafka producer.
    * @return
    */
  def build(): IProducer[K, V] = {
    deliverySemantics.foreach(ds => props.putAll(DeliverySemantics.propsFor(ds)))
    props.putAll(propsForSerializers.asJava)
    new DefaultProducer[K, V](props, defaultTopic)
  }

  private def propsForSerializers: Map[String, String] = {
    Map(
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> Serializers.serializerFor(keyClz),
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> Serializers.serializerFor(valueClz)
    )
  }
}

object ProducerBuilder {
  /**
    * Create new KafkaProducerBuilder with empty properties.
    * @param keyTag
    * @param valueTag
    * @tparam K
    * @tparam V
    * @return
    */
  def newBuilder[K, V](implicit keyTag: ClassTag[K], valueTag: ClassTag[V]): ProducerBuilder[K, V] = {
    newBuilder(new Properties())
  }

  /**
    * Create new KafkaProducerBuilder.
    * @tparam K
    * @tparam V
    * @return
    */
  def newBuilder[K, V](props: Properties)
                      (implicit keyTag: ClassTag[K], valueTag: ClassTag[V]): ProducerBuilder[K, V] = {
    val keyClz = keyTag.runtimeClass.asInstanceOf[Class[K]]
    val valueClz = valueTag.runtimeClass.asInstanceOf[Class[V]]
    newBuilder(keyClz, valueClz, props)
  }

  /**
    * Create new KafkaProducerBuilder.
    * @param keyClz
    * @param valueClz
    * @param props
    * @tparam K
    * @tparam V
    * @return
    */
  def newBuilder[K, V](keyClz: Class[K], valueClz: Class[V], props: Properties)
  : ProducerBuilder[K, V] = {
    new ProducerBuilder(props, keyClz, valueClz)
  }
}
