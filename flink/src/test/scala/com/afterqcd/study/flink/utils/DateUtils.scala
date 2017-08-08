package com.afterqcd.study.flink.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.function.Supplier

/**
  * Created by afterqcd on 2017/4/1.
  */
object DateUtils {
  private val dateFormatterThreadLocal = ThreadLocal.withInitial(
    new Supplier[SimpleDateFormat] {
      override def get(): SimpleDateFormat = new SimpleDateFormat("HH:mm:ss")
    }
  )

  def parse(source: String): Long =
    dateFormatterThreadLocal.get().parse(source).getTime

  def format(time: Long): String =
    dateFormatterThreadLocal.get().format(new Date(time))

  def now: Long = System.currentTimeMillis()
}
