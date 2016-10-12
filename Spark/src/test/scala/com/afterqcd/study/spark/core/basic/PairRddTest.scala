package com.afterqcd.study.spark.core.basic

import com.afterqcd.study.spark.SparkTest

/**
  * Created by afterqcd on 2016/10/12.
  */
class PairRddTest extends SparkTest {
  "PairRDDFunctions" should "combineByKey:按key化简元素，目标值与元素值类型可以不同" in {
    val pairs = sc.parallelize(Seq(1, 2, 3, 4)).keyBy(_ % 2)
    val combinedPairs = pairs.combineByKey(
      (e: Int) => e.toString,
      (s: String, e: Int) => s + e,
      (s1: String, s2: String) => s1 + s2
    ).collectAsMap()

    combinedPairs(0) should contain theSameElementsAs Seq('2', '4')
    combinedPairs(1) should contain theSameElementsAs Seq('1', '3')
  }

  it should "reduceByKey:按key化简元素" in {
    val pairs = sc.parallelize(Seq(1, 2, 3, 4)).keyBy(_ % 2)
    pairs.reduceByKey(_ + _)
      .collectAsMap() should contain theSameElementsAs Seq(0 -> 6, 1 -> 4)
  }

  it should "aggregateByKey:按key化简元素，目标值与元素值类型类型可以不同，且可以提供默认值" in {
    val pairs = sc.parallelize(Seq(1, 2, 3, 4)).keyBy(_ % 2)
    val combinedPairs = pairs.aggregateByKey("")(
      (s: String, e: Int) => s + e,
      (s1: String, s2: String) => s1 + s2
    ).collectAsMap()

    combinedPairs(0) should contain theSameElementsAs Seq('2', '4')
    combinedPairs(1) should contain theSameElementsAs Seq('1', '3')
  }

  it should "foldByKey:按key化简元素，目标值与元素值类型相同，可以提供初始值" in {
    val pairs = sc.parallelize(Seq(1, 2, 3, 4)).keyBy(_ % 2)
    pairs.foldByKey(0)(_ + _)
      .collectAsMap() should contain theSameElementsAs Seq(0 -> 6, 1 -> 4)
  }

  it should "countByKey:按key统计元素数量" in {
    val pairs = sc.parallelize(Seq(1, 2, 3, 4)).keyBy(_ % 2)
    pairs.countByKey() should contain theSameElementsAs Seq(0 -> 2, 1 -> 2)
  }

  it should "groupByKey:按key分组元素" in {
    val pairs = sc.parallelize(Seq(1, 2, 3, 4)).keyBy(_ % 2)
    val groupedPairs = pairs.groupByKey().collectAsMap()
    groupedPairs(0) should contain theSameElementsAs Seq(2, 4)
    groupedPairs(1) should contain theSameElementsAs Seq(1, 3)
  }

  it should "join:按key与另一个RDD做内连接" in {
    val left = sc.parallelize(Seq(1, 2, 3)).keyBy(_ % 2)
    val right = sc.parallelize(Seq(1)).keyBy(_ % 2)
    val joined = left.join(right).collect()
    joined should contain theSameElementsAs Seq(
      (1, (1, 1)), (1, (3, 1))
    )
  }

  it should "leftOuterJoin:按key与另一个RDD做左外连接" in {
    val left = sc.parallelize(Seq(1, 2, 3)).keyBy(_ % 2)
    val right = sc.parallelize(Seq(1)).keyBy(_ % 2)
    val joined = left.leftOuterJoin(right).collect()
    joined should contain theSameElementsAs Seq(
      (1, (1, Some(1))), (1, (3, Some(1))), (0, (2, None))
    )
  }

  it should "mapValues:key不变，value一对一映射" in {
    val pairs = sc.parallelize(Seq(1, 2, 3, 4)).keyBy(_ % 2)
    val doubledPairs = pairs.mapValues(_ * 2)
    doubledPairs.collect() should contain theSameElementsAs Seq(
      (1, 2), (0, 4), (1, 6), (0, 8)
    )
  }

  it should "flatMapValues:key不变，value做一对多映射并展开" in {
    val pairs = sc.parallelize(Seq(1, 2)).keyBy(_ % 2)
    pairs.flatMapValues(1 to _).collect() should contain theSameElementsAs Seq(
      (1, 1), (0, 1), (0, 2)
    )
  }

  it should "cogroup:与另一个RDD一起按key分组并做外连接" in {
    val pairs1 = sc.parallelize(Seq(1, 2, 3)).keyBy(_ % 2)
    val pairs2 = sc.parallelize(Seq(1)).keyBy(_ % 2)
    val cogrouped = pairs1.cogroup(pairs2).collectAsMap()
    cogrouped(0)._1 should contain only 2
    cogrouped(0)._2 should have size 0
    cogrouped(1)._1 should contain theSameElementsAs Seq(1, 3)
    cogrouped(1)._2 should contain only 1
  }

  it should "lookup:查询指定key对应的所有value" in {
    val pairs = sc.parallelize(Seq(1, 2, 3, 4)).keyBy(_ % 2)
    pairs.lookup(0) should contain theSameElementsAs Seq(2, 4)
  }
}
