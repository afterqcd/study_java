//package com.afterqcd.study.spark.sql.core
//
//import org.apache.spark.sql.SQLContext
//import org.apache.spark.{SparkConf, SparkContext}
//
///**
//  * Created by afterqcd on 16/6/21.
//  */
//object Core {
//
//  def main(args: Array[String]) {
//    val sc = createSparkContext("sql", "local[4]")
//    val sqlContext = new SQLContext(sc)
//
//    createDataFrame(sqlContext)
//    executeSql(sqlContext)
//    createDataSet(sqlContext)
//    createDataSetFromRdd(sqlContext)
//
//    sc.stop()
//  }
//
//  private def createSparkContext(appName: String, master: String) = {
//    val conf = new SparkConf().setAppName(appName).setMaster(master)
//    new SparkContext(conf)
//  }
//
//  private def createDataFrame(sqlContext: SQLContext): Unit = {
//    val file = resourceFile("people.json")
//    val people = sqlContext.read.json(file).cache()
//    people.show()
//    people.printSchema()
//    people.select("name").show()
//    people.select(people("name")).show()
//    people.filter(people("age") > 21).show()
//  }
//
//  private def resourceFile(name: String) = {
//    getClass.getClassLoader.getResource(name).getPath
//  }
//
//  private def executeSql(sqlContext: SQLContext) = {
//    val file = resourceFile("people.json")
//    val people = sqlContext.read.json(file)
//    people.createOrReplaceTempView("people")
//
//    import sqlContext.sparkSession.implicits._
//
//    val teenagers = sqlContext.sql("SELECT name, age FROM people WHERE age >= 13 AND age <= 19").cache()
//    teenagers.show()
//    teenagers.map(_.getAs[String]("name")).collect().foreach(println)
//  }
//
//  private def createDataSet(sqlContext: SQLContext) = {
//    val file = resourceFile("people.json")
//    import sqlContext.implicits._
////    val people = sqlContext.read.json(file).as[People].cache() // Dataset[People]
//    val people = sqlContext.sql(s"SELECT age, name FROM json.`$file` WHERE age > 10").as[People] // Dataset[People]
//    people.show()
//  }
//
//  private def createDataSetFromRdd(sqlContext: SQLContext) = {
//    import sqlContext.implicits._
//    val peopleRdd = sqlContext.sparkContext.parallelize(Seq(People(Some(15), "Justin")))
//    val people = peopleRdd.toDS() // Dataset[People]
//    people.show()
//  }
//
//
//}
//
//
//
//
