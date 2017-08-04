package com.afterqcd.study.spark.core.advanced

import com.afterqcd.study.spark.SparkTest

/**
  * Created by afterqcd on 2016/10/11.
  */
class RepartitionTest extends SparkTest {
  /**
    * repartition将RDD中的元素重新划分到指定数据的分区。
    * 重新分区的场景：
    * * 结果量小，汇总到一个分区输出，方便后续处理；
    * * 待处理的数据量大但分区数少，导致分区处理时OOM，可指定更大的分区避免OOM；
    * * 分区数少，少于分配给Job的CPU核心数，导致并行度低，可指定更大的分区数提升并行度。
    * 重新分区需要做的权衡：
    * * 分区数越多，Task越多，调度开销越大；
    * * 分区数越少，每个分区的数据量越大，JVM的内存压力越大，甚至可能OOM；
    * * 分区数小于分配的Job核心数时，并行度降低。
    * 有很多transform支持在转换的同时进行重新分区：groupByKey、reduceByKey等等
    */
  "RDD" should "repartition:重新划分分区" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8))
    elems.getNumPartitions should be (2)

    val repartitionedElems = elems.repartition(3)
    repartitionedElems.getNumPartitions should be (3)
    repartitionedElems.collect() should contain theSameElementsAs Seq(1, 2, 3, 4, 5, 6, 7, 8)
  }
}
