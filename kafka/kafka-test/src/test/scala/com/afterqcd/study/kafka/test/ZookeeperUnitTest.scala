package com.afterqcd.study.kafka.test

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by afterqcd on 2016/11/22.
  */
class ZookeeperUnitTest extends FlatSpec with Matchers with BeforeAndAfter {
  var zkUnit: ZookeeperUnit = _

  before {
    zkUnit = new ZookeeperUnit
  }

  after {
    zkUnit.shutdown()
    zkUnit = null
  }

  "ZookeeperUnit" should "支持创建Zookeeper服务实例" in {
    zkUnit.zookeeper should not be null
    zkUnit.zookeeper.port should not be 0
  }

  it should "支持创建Zookeeper客户端工具类实例" in {
    zkUnit.zkUtils should not be null
  }
}
