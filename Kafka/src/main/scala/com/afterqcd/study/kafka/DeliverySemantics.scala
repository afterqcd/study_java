package com.afterqcd.study.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.ProducerConfig

/**
  * Created by afterqcd on 2016/11/29.
  */
object DeliverySemantics {
  /**
    * Send message at most once without duplication, but with possible lost.
    */
  val AtMostOnce = "at-most-once"

  /**
    * Send message at least once without lost, but with possible duplication.
    */
  val AtLeastOnce = "at-least-once"

  /**
    * Get properties by message delivery semantics.
    * @param deliverySemantics
    * @return
    */
  def propsForProducer(deliverySemantics: String): Properties = {
    deliverySemantics match {
      case AtMostOnce => propsForAtMostOnce
      case AtLeastOnce => propsForAtLeastOnce
      case _ =>
        throw new IllegalArgumentException(s"$deliverySemantics is an illegal message delivery semantics")
    }
  }

  private def propsForAtMostOnce: Properties = {
    val props = new Properties()
    props.put(ProducerConfig.ACKS_CONFIG, "0")
    props.put(ProducerConfig.RETRIES_CONFIG, "0")
    props
  }

  private def propsForAtLeastOnce: Properties = {
    val props = new Properties()
    props.put(ProducerConfig.ACKS_CONFIG, "all")
    props.put(ProducerConfig.RETRIES_CONFIG, "2")
    props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1")
    props
  }
}
