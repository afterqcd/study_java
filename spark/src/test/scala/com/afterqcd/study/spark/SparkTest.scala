package com.afterqcd.study.spark

import org.apache.spark.SparkContext
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

/**
  * Created by afterqcd on 2016/10/11.
  */
class SparkTest extends FlatSpec with Matchers with BeforeAndAfterAll {
  var sc: SparkContext = _

  override protected def beforeAll(): Unit = {
    sc = Spark.createContext("local[2]")
  }

  override protected def afterAll(): Unit = {
    sc.stop()
  }
}
