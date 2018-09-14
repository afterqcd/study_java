package com.afterqcd.study.spark.core.advanced

import com.afterqcd.study.spark.util.SparkTest

/**
  * Created by afterqcd on 2016/10/11.
  */
class CacheTest extends SparkTest {
  "RDD" should "多次使用（transform/action）会造成重复加载和计算" in {
    val elems = sc.parallelize(Seq(1, 2, 3))
    val mapCount = sc.accumulator(0L)
    val doubledElems = elems.map { e =>
      mapCount += 1
      println(s"${e * 2}")
      e * 2
    }

    doubledElems.count() should be (3)
    doubledElems.sum() should be (12)
    mapCount.value should be (6)
  }

  it should "通过缓存来避免重复加载和计算" in {
    val elems = sc.parallelize(Seq(1, 2, 3))
    val mapCount = sc.accumulator(0L)
    val doubledElems = elems.map { e =>
      mapCount += 1
      println(s"${e * 2}")
      e * 2
    }.cache()

    doubledElems.count() should be (3)
    doubledElems.sum() should be (12)
    mapCount.value should be (3)
  }
}
