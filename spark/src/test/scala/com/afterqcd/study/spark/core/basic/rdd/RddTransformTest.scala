package com.afterqcd.study.spark.core.basic.rdd

import com.afterqcd.study.spark.util.SparkTest

/**
  * Created by afterqcd on 2016/10/11.
  */
class RddTransformTest extends SparkTest {
  "RDD" should "map:将元素一对一转换" in {
    val elems = sc.parallelize(Seq(1, 2, 3))
    elems.map(_ * 2).collect() should contain theSameElementsAs Seq(2, 4, 6)
  }

  it should "flatMap:将元素一对多转换并展开" in {
    val elems = sc.parallelize(Seq(1, 2, 3))
    elems.flatMap(elem => 1 to elem).collect() should contain theSameElementsAs Seq(1, 1, 2, 1, 2, 3)

    elems.flatMap { e =>
      if (e % 2 != 0) Some(e) else None
    }.collect() should contain theSameElementsAs Seq(1, 3)
  }

  it should "filter:保留满足条件的元素" in {
    val elems = sc.parallelize(Seq(1, 2, 3))
    elems.filter(_ % 2 != 0).collect() should contain theSameElementsAs Seq(1, 3)
  }

  it should "distinct:对所有元素去重" in {
    val elems = sc.parallelize(Seq(1, 2, 1))
    elems.distinct().collect() should contain theSameElementsAs Seq(1, 2)
  }

  it should "repartition:重新划分分区" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8))

    val repartitionedElems = elems.repartition(3)
    repartitionedElems.collect() should contain theSameElementsAs Seq(1, 2, 3, 4, 5, 6, 7, 8)
  }

  it should "sortBy:对所有元素排序" in {
    val elems = sc.parallelize(Seq(3, 4, 1, 2))
    elems.sortBy(e => e).collect() should contain theSameElementsInOrderAs Seq(1, 2, 3, 4)
  }

  it should "union:合并另一个RDD" in {
    val elems1 = sc.parallelize(Seq(1, 2))
    val elems2 = sc.parallelize(Seq(3, 4))
    elems1.union(elems2).collect() should contain theSameElementsAs Seq(1, 2, 3, 4)
  }

  it should "intersection:与另一个RDD求交集" in {
    val elems1 = sc.parallelize(Seq(1, 2))
    val elems2 = sc.parallelize(Seq(1, 4))
    elems1.intersection(elems2).collect() should contain only 1
  }

  it should "subtract:删除另一个RDD中存在的元素" in {
    val elems1 = sc.parallelize(Seq(1, 2))
    val elems2 = sc.parallelize(Seq(1, 4))
    elems1.subtract(elems2).collect() should contain only 2
  }

  it should "cartesian:与另一个RDD的笛卡尔积" in {
    val keys = sc.parallelize(Seq("a", "b"))
    val values = sc.parallelize(Seq(1, 2))
    keys.cartesian(values).collect() should contain theSameElementsAs Seq(
      ("a", 1), ("a", 2), ("b", 1), ("b", 2)
    )
  }

  it should "groupBy:按key分组" in {
    val pairs = sc.parallelize(Seq(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4)
    ))
    val grouped = pairs.groupBy(_._1).collectAsMap()
    grouped("a") should contain theSameElementsAs Seq(("a", 1), ("a", 2))
    grouped("b") should contain theSameElementsAs Seq(("b", 3), ("b", 4))
  }

  /**
    * mapPartitions每个分区调用一次。
    * 可以做些初始化工作，这样每个分区只需初始化一次，提升性能。
    * 与广播变量配合使用是经典模式。
    */
  it should "mapPartitions:在分区级映射数据" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8))
    elems.mapPartitions { es =>
      val valid = Set(3, 5, 7)
      es.filter(valid.contains)
    }.collect() should contain theSameElementsAs Seq(3, 5, 7)
  }
}
