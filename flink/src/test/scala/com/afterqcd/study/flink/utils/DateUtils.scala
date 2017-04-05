package com.afterqcd.study.flink.utils

import java.text.SimpleDateFormat
import java.util.Date

/**
  * Created by afterqcd on 2017/4/1.
  */
object DateUtils {
  private val dateFormatter = new SimpleDateFormat("HH:mm:ss")

  def parse(source: String): Long = dateFormatter.parse(source).getTime

  def format(time: Long): String = dateFormatter.format(new Date(time))

  def now: Long = System.currentTimeMillis()
}
