package com.afterqcd.study.kafka.test

import org.apache.kafka.clients.consumer.ConsumerRecord

/**
  * Created by afterqcd on 2016/12/6.
  */
case class KeyValueRecord[K, V](key: K, value: V) {
  def this(consumerRecord: ConsumerRecord[K, V]) = this(consumerRecord.key(), consumerRecord.value())
}
