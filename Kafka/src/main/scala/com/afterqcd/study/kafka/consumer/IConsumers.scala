package com.afterqcd.study.kafka.consumer

/**
  * Created by afterqcd on 2016/12/1.
  */
trait IConsumers[K, V] {
  /**
    * Start consumers to pull records.
    */
  def start(): Unit

  /**
    * Close consumers.
    */
  def close(): Unit
}
