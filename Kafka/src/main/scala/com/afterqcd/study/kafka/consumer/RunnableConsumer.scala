package com.afterqcd.study.kafka.consumer

import java.util
import java.util.Properties

import com.afterqcd.study.kafka.DeliverySemantics
import org.apache.kafka.clients.consumer.{CommitFailedException, ConsumerRebalanceListener, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.errors.WakeupException

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/12/1.
  */
class RunnableConsumer[K, V](props: Properties, recordsListener: Seq[ConsumerRecord[K, V]] => Unit)
  extends Runnable {
  val consumer = new KafkaConsumer[K, V](props)
  val topics = props.get("topics").toString.split(",").toSeq
  val deliverySemantics = props.get("delivery.semantics").toString

  override def run(): Unit = {
    try {
      consumer.subscribe(topics.asJava, rebalanceListener)
      while (true) {
        val records = consumer.poll(Long.MaxValue).asScala.toSeq
        consumeByDeliverySemantics(records)
      }
    } catch {
      case e: WakeupException =>
      case e: Throwable =>
        println(s"Unexpected error: ${e.getMessage}")
    } finally {
      try {
        doCommitSync()
      } finally {
        consumer.close()
      }
    }
  }

  private def rebalanceListener: ConsumerRebalanceListener = new ConsumerRebalanceListener {
    override def onPartitionsAssigned(partitions: util.Collection[TopicPartition]): Unit = {
      doCommitSync()
    }

    override def onPartitionsRevoked(partitions: util.Collection[TopicPartition]): Unit = {

    }
  }

  private def doCommitSync(): Unit = {
    try {
      consumer.commitSync()
    } catch {
      case e: WakeupException =>
        // we're shutting down, but finish the commit first and then
        // rethrow the exception so that the main loop can exit
        doCommitSync()
        throw e
      case e: CommitFailedException =>
        // the commit failed with an unrecoverable error. if there is any
        // internal state which depended on the commit, you can clean it
        // up here. otherwise it's reasonable to ignore the error and go on
        println("Commit failed")
    }
  }

  private def consumeByDeliverySemantics(records: Seq[ConsumerRecord[K, V]]): Unit = {
    if (deliverySemantics.equals(DeliverySemantics.AtLeastOnce)) {
      recordsListener(records)
      consumer.commitAsync()
    } else {
      doCommitSync()
      recordsListener(records)
    }
  }

  def close(): Unit = {
    consumer.wakeup()
  }
}
