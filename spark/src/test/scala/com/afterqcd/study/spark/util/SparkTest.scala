package com.afterqcd.study.spark.util

import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by afterqcd on 2016/10/11.
  */
trait SparkTest extends FlatSpec with Matchers with SharedSparkContext {
}
