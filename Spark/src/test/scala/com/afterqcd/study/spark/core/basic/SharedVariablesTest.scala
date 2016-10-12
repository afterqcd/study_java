package com.afterqcd.study.spark.core.basic

import com.afterqcd.study.spark.SparkTest
import org.apache.spark.AccumulatorParam

/**
  * Created by afterqcd on 2016/10/12.
  */
class SharedVariablesTest extends SparkTest {
  "Spark" can "通过广播变量在集群中共享少量数据" in {
    val setBc = sc.broadcast(Set(1, 3))
    val elems = sc.parallelize(Seq(2, 3, 4, 5))
    elems.mapPartitions { es =>
      val set = setBc.value
      es.filter(set.contains)
    }.collect() should contain only 3
  }

  it can "通过累加变量在集群中方便收集统计信息" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4, 5))

    val sum = sc.accumulator(0L)
    val concat = sc.accumulator("")(new AccumulatorParam[String] {
      override def addInPlace(r1: String, r2: String): String = r1 + r2
      override def zero(initialValue: String): String = ""
    })

    elems.foreach { e =>
      sum += e
      concat += e.toString
    }

    sum.value should be (15L)
    concat.value should contain theSameElementsAs Seq('1', '2', '3', '4', '5')
  }
}
