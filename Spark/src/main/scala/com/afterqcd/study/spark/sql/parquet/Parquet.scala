package com.afterqcd.study.spark.sql.parquet

import org.apache.spark.sql.{DataFrame, SQLContext}
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
      People("bob", 15, "male", "US"),
      People("jimmy", 25, "male", "US"),
      People("cathy", 30, "female", "CN"),
      People("zhangsan", 50, "male", "CN"),
      People("wangwu", 50, "male", "CN"),
      People("bob", 15, "male", "US"),
      People("jimmy", 25, "male", "US"),
      People("cathy", 30, "female", "CN"),
      People("zhangsan", 50, "male", "CN"),
      People("wangwu", 50, "male", "CN")
    ))

    import sqlContext.implicits._
    val people: DataFrame = peopleRDD.toDF()
    people.write
      .partitionBy("gender", "country")
      .parquet("people.parquet")
  }

  private def read(sqlContext: SQLContext): Unit = {
    import sqlContext.implicits._
    val people = sqlContext.read.parquet("people.parquet").as[People]
    people.printSchema()
    people.show()
  }
}




