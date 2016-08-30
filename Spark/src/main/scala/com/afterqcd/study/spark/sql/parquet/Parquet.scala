package com.afterqcd.study.spark.sql.parquet

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by afterqcd on 16/6/28.
  */
object Parquet {

  def main(args: Array[String]): Unit = {
    val sc = createSparkContext("parquet", "local[1]")
    val sqlContext = new SQLContext(sc)

    write(sc, sqlContext)
    read(sqlContext)
  }

  private def createSparkContext(appName: String, master: String) = {
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    new SparkContext(conf)
  }

  private def write(sc: SparkContext, sqlContext: SQLContext): Unit = {
    val peopleRDD = sc.parallelize(Seq(
      new  People("bob", 15, "male", "US"),
      new  People("jimmy", 25, "male", "US"),
      new  People("cathy", 30, "female", "CN"),
      new  People("zhangsan", 50, "male", "CN"),
      new  People("wangwu", 50, "male", "CN"),
      new  People("bob", 15, "male", "US"),
      new  People("jimmy", 25, "male", "US"),
      new  People("cathy", 30, "female", "CN"),
      new  People("zhangsan", 50, "male", "CN"),
      new  People("wangwu", 50, "male", "CN")
    ))

    val people = sqlContext.createDataFrame(peopleRDD)
    people.printSchema()

    people.write
      .partitionBy("gender", "country")
      .parquet("people.parquet")
  }

  private def read(sqlContext: SQLContext): Unit = {
    val people = sqlContext.read.parquet("people.parquet")
    people.printSchema()
    people.show()
  }
}




