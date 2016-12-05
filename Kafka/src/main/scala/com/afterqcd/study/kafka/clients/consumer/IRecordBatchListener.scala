package com.afterqcd.study.kafka.clients.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord

/**
  * Created by afterqcd on 2016/12/3.
  */
trait IRecordBatchListener[K, V] {
  def onRecords(records: Array[ConsumerRecord[K, V]])
}
