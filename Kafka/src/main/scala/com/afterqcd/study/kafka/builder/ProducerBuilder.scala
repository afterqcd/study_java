package com.afterqcd.study.kafka.builder

import java.util.Properties

import com.afterqcd.study.kafka.DeliverySemantics
import com.afterqcd.study.kafka.producer.{DefaultProducer, IProducer}
import org.apache.kafka.clients.producer.ProducerConfig

import scala.collection.JavaConverters._
import scala.reflect.ClassTag

/**
  * Created by afterqcd on 2016/11/29.
  */
class ProducerBuilder[K, V](val props: Properties, val keyClz: Class[K], val valueClz: Class[V])
  extends Builder[ProducerBuilder[K, V]] {
  private var defaultTopic: Option[String] = None

  /**
    * Set default topic send messages to.
    *
    * @param topic
    * @return
    */
  def defaultTopic(topic: String): ProducerBuilder[K, V] = {
    this.defaultTopic = Some(topic)
    this
  }

  /**
    * Build kafka producer.
    *
    * @return
    */
  def build(): IProducer[K, V] = {
    deliverySemantics.foreach(ds => props.putAll(DeliverySemantics.propsForProducer(ds)))
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
    * Create new Producer builder.
    *
    * @param props
    * @param keyTag
    * @param valueTag
    * @tparam K
    * @tparam V
    * @return
    */
  def apply[K, V](props: Properties = new Properties())
                 (implicit keyTag: ClassTag[K], valueTag: ClassTag[V]): ProducerBuilder[K, V] = {
    val keyClz = keyTag.runtimeClass.asInstanceOf[Class[K]]
    val valueClz = valueTag.runtimeClass.asInstanceOf[Class[V]]
    new ProducerBuilder[K, V](props, keyClz, valueClz)
  }
}
