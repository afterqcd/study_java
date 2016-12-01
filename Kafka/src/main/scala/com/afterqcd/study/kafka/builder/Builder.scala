package com.afterqcd.study.kafka.builder

import java.util.Properties

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.ProducerConfig

/**
  * Created by afterqcd on 2016/12/1.
  */
trait Builder[T] { self: T =>
  /**
    * Config properties for builder.
    * @return
    */
  def props: Properties

  /**
    * Message delivery semantics.
    */
  protected var deliverySemantics: Option[String] = None

  /**
    * Add property for producer.
    * @param name
    * @param value
    * @return
    */
  def prop(name: String, value: String): T = {
    props.put(name, value)
    self
  }

  /**
    * Set client id.
    * @param clientId
    * @return
    */
  def clientId(clientId: String): T = {
    prop(CommonClientConfigs.CLIENT_ID_CONFIG, clientId)
    self
  }

  /**
    * Set bootstrap servers.
    * @param bootstrapServers
    * @return
    */
  def bootstrapServers(bootstrapServers: String): T = {
    prop(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    self
  }

  /**
    * Set message delivery semantics.
    * DeliverySemantics.AtMostOnce: you can get quickest performance with possible lost
    * DeliverySemantics.AtLeastOnce: you can get strongest durability with reduced performance
    * @param deliverySemantics
    * @return
    */
  def messageDeliverySemantics(deliverySemantics: String): T = {
    self.deliverySemantics = Some(deliverySemantics)
    self
  }
}
