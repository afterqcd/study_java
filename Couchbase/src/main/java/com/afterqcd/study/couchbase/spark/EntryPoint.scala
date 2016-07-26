package com.afterqcd.study.couchbase.spark

import com.couchbase.client.java.CouchbaseCluster
import com.couchbase.client.java.query.N1qlQuery
import com.couchbase.spark.rdd.QueryRDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.EqualTo
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.JavaConverters._
import Utils._

/**
  * Created by afterqcd on 16/7/21.
  */
object EntryPoint {

  def main(args: Array[String]) {
    val sc = createSparkContext

    println("stats by query rdd")
    elapse { statsByQueryRdd(sc) }

    println("stats by spark sql")
    elapse { statsBySparkSql(sc) }

    println("stats by N1ql join")
    statsByN1qlJoin()
  }

  private def createSparkContext: SparkContext = {
    val conf = new SparkConf().setAppName("Spark on Couchbase").setMaster("local[1]")
      .set("com.couchbase.bucket.travel-sample", "")
      .set("com.couchbase.nodes", "172.16.185.248")
    val sc = new SparkContext(conf)
    sc
  }

  private def statsByN1qlJoin(): Unit = {
    val cluster = CouchbaseCluster.create("172.16.185.248")
    val bucket = cluster.openBucket("travel-sample")

    try {
      elapse {
        val result = bucket.query(N1qlQuery.simple("select airline.country, count(*) as routecount"
          + " from `travel-sample` route"
          + " join `travel-sample` airline on keys route.airlineid"
          + " where route.type = 'route' and airline.type = 'airline'"
          + " group by airline.country"
        ))

        for (row <- result.asScala) println(row.value())
      }
    } finally {
      cluster.disconnect()
    }
  }

  private def statsBySparkSql(sc: SparkContext): Unit = {
    val sql = new SQLContext(sc)

    import com.couchbase.spark.sql._
    val airlines = sql.read.couchbase(schemaFilter = EqualTo("type", "airline"))
    val routes = sql.read.couchbase(schemaFilter = EqualTo("type", "route"))

    val count = routes.select("airlineid").repartition(10).join(
      airlines.selectExpr("META_ID as airlineid", "country"),
      usingColumn = "airlineid"
    ).map(r => r.getAs[String]("country")).countByValue()

    println(count)
  }

  private def statsByQueryRdd(sc: SparkContext): Unit = {
    val airLines = QueryRDD(sc, "travel-sample",
      N1qlQuery.simple("select META(`travel-sample`).id as airlineid, country from `travel-sample` where type = 'airline'")
    ).map(row => (row.value.getString("airlineid"), row.value.getString("country"))).collectAsMap()

    val bcAirlines = sc.broadcast(airLines)

    val routes = QueryRDD(sc, "travel-sample",
      N1qlQuery.simple("select airlineid from `travel-sample` where type = 'route'")
    )

    val count = routes.mapPartitions { rows =>
      val airLineMap = bcAirlines.value
      rows.flatMap(r => airLineMap.get(r.value.getString("airlineid")))
    }.countByValue()

    println(count)
  }
}
