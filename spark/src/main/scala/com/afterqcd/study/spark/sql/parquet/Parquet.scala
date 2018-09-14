//package com.afterqcd.study.spark.sql.parquet
//
//import com.google.gson.Gson
//import org.apache.spark.sql.SQLContext
//import org.apache.spark.{SparkConf, SparkContext}
//
///**
//  * Created by afterqcd on 16/6/28.
//  */
//object Parquet {
//
//  def main(args: Array[String]): Unit = {
//    val sc = createSparkContext("parquet", "local[4]")
//    val sqlContext = new SQLContext(sc)
//
////    write(sc, sqlContext)
////    read(sqlContext)
//    readMemberBehavior(sqlContext)
//  }
//
//  private def createSparkContext(appName: String, master: String) = {
//    val conf = new SparkConf().setAppName(appName).setMaster(master)
//    new SparkContext(conf)
//  }
//
//  private def write(sc: SparkContext, sqlContext: SQLContext): Unit = {
//    val peopleRDD = sc.parallelize(Seq(
//      People("bob", 15, "male", "US"),
//      People("jimmy", 25, "male", "US"),
//      People("cathy", 30, "female", "CN"),
//      People("zhangsan", 50, "male", "CN"),
//      People("wangwu", 50, "male", "CN"),
//      People("bob", 15, "male", "US"),
//      People("jimmy", 25, "male", "US"),
//      People("cathy", 30, "female", "CN"),
//      People("zhangsan", 50, "male", "CN"),
//      People("wangwu", 50, "male", "CN")
//    ))
//
//    val people = sqlContext.createDataFrame(peopleRDD)
//    people.printSchema()
//
//    people.write
//      .partitionBy("gender", "country")
//      .parquet("people.parquet")
//  }
//
//  private def read(sqlContext: SQLContext): Unit = {
//    val people = sqlContext.read.parquet("people.parquet")
//    people.printSchema()
//    people.show()
//  }
//
//  val gson: Gson = new Gson()
//
//  def readMemberBehavior(sqlContext: SQLContext): Unit = {
//    val dates = Seq("20160830", "20160831", "20160901", "20160902", "20160903", "20160904", "20160905", "20160906", "20160907")
//    val paths = dates.map("/Users/afterqcd/Desktop/normalized/" + _)
//    val memberBehavior = sqlContext.read.parquet(paths: _*)
//
//    import sqlContext.sparkSession.implicits._
//
//    memberBehavior.map { row =>
//      MemberBehavior(
//        date = row.getAs[String]("theDate"),
//        aid = row.getAs[String]("aid"),
//        categoryId = row.getAs[Long]("cateId"),
//        categoryName = row.getAs[String]("cateName"),
//        keyword = row.getAs[String]("keyword"),
//        behaviorType = row.getAs[String]("behaviorType"),
//        brandName = row.getAs[String]("brandName"),
//        seriesName = row.getAs[String]("seriesName")
//      )
//    }.map(gson.toJson).write.text("/Users/afterqcd/Desktop/MemberBehavior")
//  }
//}
//
//case class MemberBehavior(
//                           date: String, aid: String, categoryId: Long, categoryName: String, keyword: String,
//                           behaviorType: String, brandName: String, seriesName: String
//                         )
//
//
//
//
