package com.afterqcd.study.kafka.unit

import java.net.ServerSocket
import java.util.Properties

import kafka.admin.{AdminUtils, RackAwareMode}
import kafka.server.{KafkaConfig, KafkaServer}
import kafka.utils.{CoreUtils, TestUtils}
import org.apache.kafka.common.requests.MetadataResponse

import scala.collection.mutable.ArrayBuffer

/**
  * Created by afterqcd on 2016/11/21.
  */
class KafkaUnit {
  private val host = "127.0.0.1"
  val zkUnit = new ZookeeperUnit
  private val kafkaBrokers = ArrayBuffer.empty[KafkaServer]

  /**
    * Set up a kafka broker.
    */
  def setUpBroker(): Unit = {
    kafkaBrokers += KafkaServerFactory.createKafkaServer
  }

  /**
    * Set up multiple kafka brokers.
    * @param count
    */
  def setUpBrokers(count: Int): Unit = {
    1 to count foreach { _ =>
      setUpBroker()
    }
  }

  /**
    * Create topic.
    * @param topic
    * @param partitions
    * @param replicationFactor
    * @param topicConfig
    */
  def createTopic(
                   topic: String, partitions: Int, replicationFactor: Int,
                   topicConfig: Properties = new Properties
                 ): Unit = {
    assert(kafkaBrokers.nonEmpty, "Can not create topic before setup any broker")

    AdminUtils.createTopic(zkUnit.zkUtils, topic, partitions, replicationFactor, topicConfig, RackAwareMode.Disabled)
    0 until partitions foreach { p =>
      TestUtils.waitUntilMetadataIsPropagated(kafkaBrokers,topic, p)
      TestUtils.waitUntilLeaderIsElectedOrChanged(zkUnit.zkUtils, topic, p)
    }
  }

  /**
    * List all topics.
    * @return
    */
  def topics(): Seq[String] = {
    AdminUtils.fetchAllTopicConfigs(zkUnit.zkUtils).keys.toSeq
  }

  /**
    * Describe topic.
    * @param topic
    * @return
    */
  def describeTopic(topic: String): MetadataResponse.TopicMetadata = {
    AdminUtils.fetchTopicMetadataFromZk(topic, zkUnit.zkUtils)
  }

  /**
    * Delete topic.
    * @param topic
    */
  def deleteTopic(topic: String): Unit = {
    AdminUtils.deleteTopic(zkUnit.zkUtils, topic)
  }

  /**
    * Bootstrap servers String for producer and consumer.
    * @return
    */
  def bootstrapServers: String = {
    TestUtils.getBrokerListStrFromServers(kafkaBrokers)
  }

  /**
    * Shutdown.
    */
  def shutdown(): Unit = {
    kafkaBrokers.foreach { b =>
      CoreUtils.swallow(b.shutdown())
      CoreUtils.swallow(b.awaitShutdown())
    }
    zkUnit.shutdown()
  }

  object KafkaServerFactory {
    def createKafkaServer: KafkaServer = {
      val port = randomPort()

      val props = new Properties()

      props.put(KafkaConfig.ZkConnectProp, zkUnit.zkConnect)
      props.put(KafkaConfig.LogDirsProp, s"${TestUtils.tempDir().getAbsolutePath}")
      props.put(KafkaConfig.HostNameProp, host)
      props.put(KafkaConfig.PortProp, port.toString)
      props.put(KafkaConfig.ListenersProp, s"PLAINTEXT://$host:$port")
      props.put(KafkaConfig.ControlledShutdownMaxRetriesProp, "0")
      props.put(KafkaConfig.LogCleanerBackoffMsProp, "50")

      TestUtils.createServer(new KafkaConfig(props))
    }
  }

  private def randomPort(): Int = {
    val socket = new ServerSocket(0)
    val port = socket.getLocalPort
    socket.close()
    port
  }
}
