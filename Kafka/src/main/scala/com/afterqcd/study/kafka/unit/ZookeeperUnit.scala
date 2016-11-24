package com.afterqcd.study.kafka.unit

import kafka.utils.{CoreUtils, ZkUtils}
import kafka.zk.EmbeddedZookeeper
import org.apache.kafka.common.security.JaasUtils

/**
  * Created by afterqcd on 2016/11/22.
  */
class ZookeeperUnit {
  val host = "127.0.0.1"
  val zkConnectionTimeout = 30000
  val zkSessionTimeout = 30000

  val zookeeper: EmbeddedZookeeper = new EmbeddedZookeeper
  val zkConnect = s"$host:${zookeeper.port}"
  val zkUtils = ZkUtils(zkConnect, zkSessionTimeout, zkConnectionTimeout, JaasUtils.isZkSecurityEnabled)

  def shutdown(): Unit = {
    CoreUtils.swallow(zkUtils.close())
    CoreUtils.swallow(zookeeper.shutdown())
  }
}
