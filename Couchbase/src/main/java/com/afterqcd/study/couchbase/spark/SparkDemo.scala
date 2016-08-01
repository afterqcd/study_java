package com.afterqcd.study.couchbase.spark

import com.couchbase.client.java.CouchbaseCluster
import com.couchbase.client.java.query.N1qlQuery
import com.couchbase.client.java.view.ViewQuery
import com.couchbase.spark.rdd.{ViewRDD, QueryRDD}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.EqualTo
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.JavaConverters._
import Utils._

/**
  * Created by afterqcd on 16/7/21.
  */
object SparkDemo {

  def main(args: Array[String]) {
    val sc = createSparkContext

    println("open bucket")
    elapse { openBucket(sc) }

//    println("stats by local file")
//    elapse(statsByLocalFiles(sc))

//    println("stats by view rdd")
//    elapse { statsByViewRdd(sc) }
//
//    println("stats by query rdd")
//    elapse { statsByQueryRdd(sc) }
//
//    println("stats by N1ql join")
//    statsByN1qlJoin()

    println("stats by spark sql")
    elapse { statsBySparkSql(sc) }
  }

  private def openBucket(sc: SparkContext): Unit = {
    QueryRDD(sc, "travel-sample",
      N1qlQuery.simple("select META(`travel-sample`).id as airlineid from `travel-sample` where type = 'airline'")
    ).count()
  }

  private def createSparkContext: SparkContext = {
    val conf = new SparkConf().setAppName("Spark on Couchbase").setMaster("local[2]")
      .set("com.couchbase.bucket.travel-sample", "")
      .set("com.couchbase.nodes", "172.16.185.248")
      .set("spark.sql.shuffle.partitions", "2")
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

    val count = routes.select("airlineid").join(
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

  private def statsByViewRdd(sc: SparkContext): Unit = {
    val airLines = QueryRDD(sc, "travel-sample",
      N1qlQuery.simple("select META(`travel-sample`).id as airlineid, country from `travel-sample` where type = 'airline'")
    ).map(row => (row.value.getString("airlineid"), row.value.getString("country"))).collectAsMap()

    val bcAirlines = sc.broadcast(airLines)
    val query = ViewQuery.from("travel", "travel_airline").reduce(false)
    val routes = ViewRDD(sc, "travel-sample",
      query
    )
    val count = routes.mapPartitions { rows =>
      val airLineMap = bcAirlines.value
      rows.flatMap(r => airLineMap.get(r.key.toString))
    }.countByValue()

    println(count)
  }

  private def outputFiles(sc: SparkContext): Unit = {
    val routes = ViewRDD(sc, "travel-sample",
      ViewQuery.from("travel", "travel_airline").reduce(false)
    ).map(_.key.toString)
    routes.saveAsTextFile("routes")

    val airLines = QueryRDD(sc, "travel-sample",
      N1qlQuery.simple("select META(`travel-sample`).id as airlineid, country from `travel-sample` where type = 'airline'")
    ).map(row => row.value.getString("airlineid") + "," + row.value.getString("country"))
    airLines.saveAsTextFile("airlines")
  }

  private def statsByLocalFiles(sc: SparkContext): Unit = {
    val routes = sc.textFile("routes")
    val airLines = sc.textFile("airlines").map { l =>
      val elems = l.split(",")
      (elems(0), elems(1))
    }.collectAsMap()
    val bcAirlines = sc.broadcast(airLines)

    val count = routes.mapPartitions { rows =>
      val airLineMap = bcAirlines.value
      rows.flatMap(r => airLineMap.get(r))
    }.countByValue()

    println(count)
  }
}
