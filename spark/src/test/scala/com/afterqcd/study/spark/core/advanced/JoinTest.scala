package com.afterqcd.study.spark.core.advanced

import com.afterqcd.study.spark.util.SparkTest

/**
  * Created by afterqcd on 2016/10/11.
  */
class JoinTest extends SparkTest {
  /**
    * map join适合一大一小两个数据集的连接。
    * 将小数据集广播到集群中的每台机器，而大数据集无需在集群中移动，极大地减少了网络带宽消耗。
    */
  "Join" should "map join适合一大一小两个数据集的连接" in {
    val smallDataSetBc = sc.broadcast(Set(1, 3))
    val bigDataSet = sc.parallelize(Seq(2, 3, 4, 5))
    bigDataSet.mapPartitions { es =>
      val smallDataSet = smallDataSetBc.value
      es.filter(smallDataSet.contains)
    }.collect() should contain only 3
  }

  /**
    * reduce join适合两个较大数据集的连接。
    * 任何一个数据集都大到无法在单机内存中常驻，如果继续使用map join会导致JVM频繁GC甚至OOM。
    * 此时可以在reduce join时设置较大的partition数，用网络带宽消耗换取运行稳定性。
    */
  it should "reduce join适合两个较大数据集的连接" in {
    val bigDataSet1 = sc.parallelize(Seq(1, 2, 3)).keyBy(_ % 2)
    val bigDataSet2 = sc.parallelize(Seq(1)).keyBy(_ % 2)
    val joined = bigDataSet1.join(bigDataSet2, 4).collect()
    joined should contain theSameElementsAs Seq(
      (1, (1, 1)), (1, (3, 1))
    )
  }
}
