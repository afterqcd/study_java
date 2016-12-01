package com.afterqcd.study.kafka.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord

/**
  * Created by afterqcd on 2016/12/1.
  */
trait IRecordBatchListener[K, V] {
  def onRecords(records: Seq[ConsumerRecord[K, V]]): Unit
}
