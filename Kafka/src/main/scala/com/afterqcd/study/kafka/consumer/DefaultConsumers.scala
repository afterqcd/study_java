package com.afterqcd.study.kafka.consumer

import java.util.Properties
import java.util.concurrent.{Executors, TimeUnit}

import com.afterqcd.study.kafka.util.CloseOnExit
import org.apache.kafka.clients.consumer.ConsumerRecord

/**
  * Created by afterqcd on 2016/12/1.
  */
class DefaultConsumers[K, V](props: Properties, concurrency: Int,
                             listener: Seq[ConsumerRecord[K, V]] => Unit)
  extends IConsumers[K, V] with CloseOnExit {

  private val consumers = 1 to concurrency map { _ => new RunnableConsumer[K, V](props, listener) }
  private val executor = Executors.newFixedThreadPool(concurrency)

  /**
    * Start consumers to pull records.
    */
  override def start(): Unit = {
    consumers.foreach(c => executor.submit(c))
  }

  override protected def doClose(): Unit = {
    println("consumers closing")
    consumers.foreach(_.close())
    executor.shutdown()
    executor.awaitTermination(5L, TimeUnit.SECONDS)
  }
}
