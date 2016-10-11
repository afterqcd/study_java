package com.afterqcd.study.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by afterqcd on 2016/10/11.
  */
object Spark {
  /**
    * Create spark context with default app name "test" and default master "local[4]".
    * @return
    */
  def createContext(): SparkContext = createContext("local[4]")

  /**
    * Create spark context with default app name "test".
    * @param master
    * @return
    */
  def createContext(master: String): SparkContext = createContext("test", master)

  /**
    * Create spark context.
    * @param appName
    * @param master
    * @return
    */
  def createContext(appName: String, master: String): SparkContext = {
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    new SparkContext(conf)
  }
}
