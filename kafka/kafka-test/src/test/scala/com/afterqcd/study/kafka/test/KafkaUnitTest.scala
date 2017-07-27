package com.afterqcd.study.kafka.test

import org.apache.kafka.common.protocol.Errors
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/11/21.
  */
class KafkaUnitTest extends FlatSpec with Matchers with BeforeAndAfter {
  var kafkaUnit: KafkaUnit = _

  before {
    kafkaUnit = new KafkaUnit
  }

  after {
    kafkaUnit.shutdown()
    kafkaUnit = null
  }

  "KafkaUnit" should "在刚创建时，没有Broker" in {
    kafkaUnit.bootstrapServers should be ("")
  }

  it should "创建单个Broker" in {
    kafkaUnit.setUpBroker()
    kafkaUnit.bootstrapServers.split(",") should have size 1
    val Array(_, port) = kafkaUnit.bootstrapServers.split(":")
    port should not be 0
  }

  it should "多次创建单个Broker" in {
    kafkaUnit.setUpBroker()
    kafkaUnit.setUpBroker()

    val bootstrapServers = kafkaUnit.bootstrapServers
    println(bootstrapServers)
    bootstrapServers.split(",") should have size 2
    ports(bootstrapServers) should have size 2
  }

  it should "一次创建多个Broker" in {
    kafkaUnit.setUpBrokers(3)

    val bootstrapServers = kafkaUnit.bootstrapServers
    bootstrapServers.split(",") should have size 3
    ports(bootstrapServers) should have size 3
  }

  it should "创建Topic" in {
    kafkaUnit.setUpBrokers(2)
    kafkaUnit.createTopic("test", 5, 2)
    kafkaUnit.topics() should contain only "test"

    val topicMetadata = kafkaUnit.describeTopic("test")
    topicMetadata.partitionMetadata() should have size 5
    topicMetadata.partitionMetadata().asScala foreach { pm =>
      pm.error() should be (Errors.NONE)
      pm.replicas() should have size 2
      pm.isr() should have size 2
    }
  }

  private def ports(bootstrapServers: String): Set[String] = {
    bootstrapServers.split(",").map(_.split(":")(1)).toSet
  }
}
