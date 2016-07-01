package com.afterqcd.study.spark.core

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable.TreeMap

/**
  * Created by afterqcd on 16/6/21.
  */
object EntryPoint {

  def main(args: Array[String]): Unit = {
    val sc = createSparkContext("core", "local[4]")

    createRddFromCollection(sc)
    createRddFromTextFile(sc)
    pairFunction(sc)
    sumByAccumulator(sc)

    sc.stop()
  }

  private def createSparkContext(appName: String, master: String): SparkContext = {
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    new SparkContext(conf)
  }

  private def createRddFromCollection(sc: SparkContext): Unit = {
    val elems = sc.parallelize(Seq(1, 3, 5, 7, 2, 4, 6, 1, 3))
    elems.cache()

    println("Count of elements " + elems.count())
    println("Count of odd elements " + elems.filter(_ % 2 == 1).count())
    println("Count by value " + (TreeMap.empty[Int, Long] ++ elems.countByValue()))
  }

  private def createRddFromTextFile(sc: SparkContext) = {
    val textFile = getClass.getClassLoader.getResource("text").getPath

    val lines = sc.textFile(textFile).cache()
    val numbers = lines.flatMap(_.split(" ")).map(_.toInt)

    println("Lines " + lines.count())
    println("Count of odd elements " + numbers.filter(_ % 2 == 1).count())
  }

  private def pairFunction(sc: SparkContext) = {
    val elems = sc.parallelize(Seq(1, 3, 5, 7, 2, 4, 6, 1, 3))
    val oddEvens = elems.keyBy(e => if (e % 2 == 1) "Odd" else "Even" ).cache()

    println("Count " + oddEvens.countByKey())
    println("Sum " + oddEvens.reduceByKey(_ + _).collectAsMap())
  }

  private def sumByAccumulator(sc: SparkContext) = {
    val elems = sc.parallelize(Seq(1, 3, 5, 7, 2, 4, 6, 1, 3)).cache()
    println("Sum by RDD.reduce " + elems.reduce(_ + _))

    val acc = sc.accumulator(0L)
    elems.foreach(acc.add(_))
    println("Sum by accumulator " + acc.value)
  }
}
