package com.afterqcd.study.couchbase.spark

/**
  * Created by afterqcd on 16/7/21.
  */
object Utils {
  def elapse(action: => Unit): Unit = {
    val start = System.currentTimeMillis()
    action
    println(s"Elapsed time ${System.currentTimeMillis() - start}")
  }
}
