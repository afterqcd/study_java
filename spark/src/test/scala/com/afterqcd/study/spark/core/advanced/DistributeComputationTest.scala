package com.afterqcd.study.spark.core.advanced

import com.afterqcd.study.spark.core.advanced.DistributeComputationTest.thPrintln
import com.afterqcd.study.spark.util.SparkTest

/**
  * Created by afterqcd on 2016/10/11.
  */
class DistributeComputationTest extends SparkTest {
  "WorkCount" should "reduceByKey使用map/reduce合并" in {
    val lines = sc.parallelize(Seq(
      "hello world", "hello scala", "hello scala", "hello spark",
      "hello world", "hello spark", "hello scala", "hello spark"
    ))

    val words = lines.flatMap { l =>
      val words = l.split(" ")
      thPrintln(s"split '$l' to ${words.mkString(",")}")
      words
    }

    val wordCounts = words.map { w =>
      thPrintln(s"map $w to ${(w, 1)}")
      (w, 1)
    }.reduceByKey { (c1, c2) =>
      thPrintln(s"reduce ${(c1, c2)} to ${c1 + c2}")
      c1 + c2
    }

    wordCounts.foreachPartition(_.foreach(thPrintln))
  }

  it should "groupByKey使用reduce合并" in {
    val lines = sc.parallelize(Seq(
      "hello world", "hello scala", "hello scala", "hello spark",
      "hello world", "hello spark", "hello scala", "hello spark"
    ))

    val words = lines.flatMap { l =>
      val words = l.split(" ")
      thPrintln(s"split '$l' to ${words.mkString(",")}")
      words
    }

    val wordCounts = words.map { w =>
      thPrintln(s"map $w to ${(w, 1)}")
      (w, 1)
    }.groupByKey().map { case (w, cs) =>
      thPrintln(s"map ${(w, cs.mkString(","))} to ${(w, cs.size)}")
      (w, cs.size)
    }

    wordCounts.foreachPartition(_.foreach(thPrintln))
  }
}

object DistributeComputationTest {
  def thPrintln(obj: Any): Unit = {
    println(s"Thread ${Thread.currentThread().getName}: $obj")
  }
}
