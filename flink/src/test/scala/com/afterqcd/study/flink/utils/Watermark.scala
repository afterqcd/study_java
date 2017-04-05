package com.afterqcd.study.flink.utils

import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * Created by afterqcd on 2017/4/2.
  */
object Watermark {
  def outOfOrderness[T](maxOutOfOrderness: Time)(timeExtractor: T => Long)
  : BoundedOutOfOrdernessTimestampExtractor[T] = {
    new BoundedOutOfOrdernessTimestampExtractor[T](maxOutOfOrderness) {
      override def extractTimestamp(element: T): Long = timeExtractor(element)
    }
  }
}
