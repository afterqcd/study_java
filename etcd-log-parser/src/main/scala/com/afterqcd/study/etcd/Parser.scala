package com.afterqcd.study.etcd

import scala.io.Source

/**
  * Created by afterqcd on 2017/3/8.
  */
class Parser(val logFile: String) {
  def entries: Seq[Entry] = {
    val lines = Source.fromFile(logFile).getLines
    val (names, values) = lines.zipWithIndex.partition { case (line, _) => line.startsWith("[") }
    val valueMap = values.map { case (value, index) => (index, value) }.toMap
    names.map { case (name, index) =>
      Entry(name, valueMap.getOrElse(index + 1, ""))
    }.toSeq
  }
}
