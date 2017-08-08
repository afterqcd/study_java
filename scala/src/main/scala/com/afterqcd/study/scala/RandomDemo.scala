package com.afterqcd.study.scala

import java.util.Random

import scala.collection.SortedMap

/**
  * Created by afterqcd on 16/8/15.
  */
object RandomDemo {
  def main(args: Array[String]) {
    val values = gaussianValues(0, 1000) ++ gaussianValues(10, 500) ++ gaussianValues(15, 500)
    stats(values)
    kMeans(values, Seq(7D, -1D, 13D))
  }

  def gaussianValues(means: Double, count: Int): Seq[Double] = {
    val random = new Random()
    Seq.fill(count) {
      random.nextGaussian() + means
    }
  }

  def stats(values: Seq[Double]): Unit = {
    val counter = values.map(v => v.round).groupBy(v => v).mapValues(_.size)
    println(SortedMap.empty[Long, Integer] ++ counter)
  }

  def kMeans(values: Seq[Double], centers: Seq[Double]): Unit = {
    val groupedValues = groupValues(values, centers).filter(_.nonEmpty)
    val newCenters = groupedValues.map(s => s.sum / s.size)

    val diff = newCenters.zip(centers).map { case (a, b) => a - b }
    if (diff.forall(_ < 0.001)) {
      newCenters.zip(groupedValues).map { case (c, vs) => (c, vs.size, vs.min, vs.max) }.foreach(println)
    } else {
      kMeans(values, newCenters)
    }
  }

  def groupValues(values: Seq[Double], centers: Seq[Double]): Seq[Seq[Double]] = {
    val indexedValues = values.groupBy { case value =>
      centers.foldLeft((0, 0, Double.MaxValue)) { case ((index, minIndex, minDistance), center) =>
        val distance = (value - center).abs
        (
          index + 1,
          if (distance < minDistance) index else minIndex,
          if (distance < minDistance) distance else minDistance
          )
      }._2
    }

    indexedValues.foreach { case (i, vs) => println(s"c$i: ${centers(i)}, distance: ${vs.map(v => (v - centers(i)).abs).sum}") }

    centers.indices.map(indexedValues.getOrElse(_, Seq.empty))
  }
}
