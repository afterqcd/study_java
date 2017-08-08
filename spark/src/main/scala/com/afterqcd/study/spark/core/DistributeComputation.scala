package com.afterqcd.study.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory

/**
  * Created by afterqcd on 2016/10/13.
  * spark-submit --master spark://shuyou01:7077 --class com.afterqcd.study.spark.core.DistributeComputation spark-1.0-SNAPSHOT.jar
  */
object DistributeComputation {
  private val logger = LoggerFactory.getLogger(DistributeComputation.getClass)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("distribute_computation")
    val sc = new SparkContext(conf)
    val lines = sc.parallelize(Seq(
      "hello world", "hello scala", "hello scala", "hello spark",
      "hello world", "hello spark", "hello scala", "hello spark"
    )).cache()

    wordCountByReduceByKey(lines)
    wordCountByGroupByKey(lines)
  }

  private def wordCountByReduceByKey(lines: RDD[String]) = {
    val words = lines.flatMap { l =>
      val words = l.split(" ")
      log("ReduceByKey", s"split '$l' to ${words.mkString(",")}")
      words
    }

    val wordCounts = words.map { w =>
      log("ReduceByKey", s"map $w to ${(w, 1)}")
      (w, 1)
    }.reduceByKey { (c1, c2) =>
      log("ReduceByKey", s"reduce ${(c1, c2)} to ${c1 + c2}")
      c1 + c2
    }

    wordCounts.foreachPartition(_.foreach(wc => log("ReduceByKey", wc)))
  }

  private def wordCountByGroupByKey(lines: RDD[String]) = {
    val words = lines.flatMap { l =>
      val words = l.split(" ")
      log("GroupByKey", s"split '$l' to ${words.mkString(",")}")
      words
    }

    val wordCounts = words.map { w =>
      log("GroupByKey", s"map $w to ${(w, 1)}")
      (w, 1)
    }.groupByKey().map { case (w, cs) =>
      log("GroupByKey", s"map ${(w, cs.mkString(","))} to ${(w, cs.size)}")
      (w, cs.size)
    }

    wordCounts.foreachPartition(_.foreach(wc => log("GroupByKey", wc)))
  }

  private def log(prefix: String, obj: Any): Unit = {
    logger.info(s"$prefix : $obj")
  }
}
