package com.afterqcd.study.spark.core.basic.rdd

import com.afterqcd.study.spark.util.SparkTest

/**
  * Created by afterqcd on 2016/10/11.
  */
class CreatingRddTest extends SparkTest {
  "RDD" should "支持从Seq直接创建" in {
    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8))
    rdd.collect() should contain theSameElementsAs Seq(1, 2, 3, 4, 5, 6, 7, 8)
  }

  it should "支持从文件加载创建" in {
    val lines = sc.textFile(getClass.getClassLoader.getResource("text").getPath)
    lines.collect() should contain theSameElementsAs Seq("1 3 5 7", "2 4 6 8")
  }
}
