package com.afterqcd.study.scala.vs

/**
  * Created by afterqcd on 2017/5/4.
  */
object SortDemo {
  def main(args: Array[String]): Unit = {
    val persons = Seq.empty[Person]
    persons
      .filter(_.name != null)
      .sortBy(p => (p.age, p.name))
  }
}
