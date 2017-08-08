package com.afterqcd.study.spark.core.basic.rdd

import com.afterqcd.study.spark.SparkTest

/**
  * Created by afterqcd on 2016/10/11.
  */
class RddActionTest extends SparkTest {
  "RDD" should "foreach:遍历所有元素" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    elems.foreach(println)
  }

  it should "collect:返回包含所有元素的数组" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    elems.collect() should contain theSameElementsAs Seq(1, 2, 3, 4)
  }

  it should "reduce:将所有元素化简为单个元素" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    elems.reduce(_ + _) should be (10)
  }

  it should "fold:相当于带初始值的reduce" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    val partitionNumber = elems.getNumPartitions
    elems.fold(1)(_ + _) should be (10 + (partitionNumber + 1) * 1)
    elems.fold(2)(_ + _) should be (10 + (partitionNumber + 1) * 2)
  }

  it should "aggregate:化简，但目标值可与元素值类型不一样" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    val result: String = elems.aggregate("")(
      _ + _, // (s: String, e: Int) => s + e,
      _ + _  // (s1: String, s2: String) => s1 + s2
    )
    result should contain theSameElementsAs Seq('1', '2', '3', '4')
  }

  it should "count:元素的数量" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    elems.count() should be (4)
  }

  /**
    * 分布式计算中经典的 Word Count。
    */
  it should "countByValue:按元素值统计数量" in {
    val lines = sc.parallelize(Seq("hello world", "hello spark"))
    val words = lines.flatMap(_.split(" "))
    words.countByValue() should contain theSameElementsAs Seq(
      "hello" -> 2, "world" -> 1, "spark" -> 1
    )
  }

  it should "take:取前N个元素" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    elems.take(1) should contain oneOf (1, 2, 3, 4)
  }

  it should "first:取第一个元素" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    Seq(1, 2, 3, 4) should contain (elems.first())
  }

  it should "top:取最大的N个元素" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    elems.top(2) should contain theSameElementsInOrderAs Seq(4, 3)
  }

  it should "max:获取最大的元素" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    elems.max() should be (4)
  }

  it should "min:获取最大的元素" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    elems.min() should be (1)
  }

  it should "直到action才开始真正执行transform链" in {
    val elems = sc.parallelize(Seq(1, 2, 3, 4))
    println("RDD创建成功")

    val doubleElems = elems.map { e =>
      println(s"$e + 2")
      e + 2
    }
    println("所有元素的值加2")

    val plus2Elems = doubleElems.map { e =>
      println(s"$e * 2")
      e * 2
    }
    println("所有元素的值乘2")

    val subtract1Elems = plus2Elems.map { e =>
      println(s"$e - 1")
      e - 1
    }
    println("所有元素的值减1")

    val sum = subtract1Elems.sum()
    println(s"总计 $sum")
  }
}
