package com.afterqcd.study.kafka.clients.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord

/**
  * Created by afterqcd on 2016/12/3.
  */
trait IRecordListener[K, V] {
  def onRecord(record: ConsumerRecord[K, V])
}
