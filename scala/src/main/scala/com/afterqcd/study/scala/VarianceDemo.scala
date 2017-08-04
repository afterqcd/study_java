package com.afterqcd.study.scala

/**
  * Created by afterqcd on 16/8/21.
  */
object VarianceDemo {

  def main(args: Array[String]) {
    calVarianceAndPrint(Seq(20, 50, 30, 20, 5))
    calVarianceAndPrint(Seq(40, 100, 60, 40, 10))
    calVarianceAndPrint(Seq(4, 10, 6, 4, 1))
    calVarianceAndPrint(Seq(6, 5, 0, 0, 0))
  }

  private def calVarianceAndPrint(values: Seq[Int]): Unit = {
    val (sum, count) = values.foldLeft((0, 0)) { case ((s, c), v) =>
      (s + v, c + 1)
    }
    val avg = sum / count.toDouble
    val variance = Math.sqrt(values.foldLeft(0D) { case (s, v) => s + (v - avg) * (v - avg) } / count)

    println(s"Values: $values, average: $avg, variance: $variance, cv: ${variance / avg}")
  }
}
