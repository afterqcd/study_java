package com.afterqcd.study.spark.graphx

import org.apache.spark.graphx.{Edge, Graph}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by afterqcd on 16/6/29.
  */
object UserMerger {
  def main(args: Array[String]): Unit = {
    val sc = createSparkContext("graphx", "local[4]")
    val nodes = sc.parallelize(Seq(
      (1L, Member("天猫", 3)),
      (2L, Member("天猫", 1)),
      (3L, Member("天虹", 2)),
      (4L, Member("飞亚达", 7)),

      (11L, Mobile(15900000000L)),
      (12L, Mobile(15986000000L)),
      (13L, Email("abc@gmail.com"))
    ))

    val relationships = sc.parallelize(Seq(
      Edge(1L, 11L, "owns"),
      Edge(2L, 12L, "owns"),
      Edge(3L, 12L, "owns"), Edge(3L, 13L, "owns"),
      Edge(4L, 13L, "owns")
    ))

    val graph = Graph(nodes, relationships)
    val userGraph = graph.subgraph(vpred = (_, d) => d.isInstanceOf[Member[Any]])
    graph.connectedComponents().mask(userGraph).vertices.collect().foreach(println)
    graph.joinVertices()
  }

  private def createSparkContext(appName: String, master: String): SparkContext = {
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    new SparkContext(conf)
  }

  trait VertexProperty
  case class Member[+T](source: String, id: T) extends VertexProperty
  trait IdentityProperty[+T] extends VertexProperty {
    def tpe: String
    def value: T
  }
  case class Mobile(value: Long) extends IdentityProperty[Long] {
    override def tpe = "mobile"
  }
  case class Email(value: String) extends IdentityProperty[String] {
    override def tpe = "email"
  }
}
