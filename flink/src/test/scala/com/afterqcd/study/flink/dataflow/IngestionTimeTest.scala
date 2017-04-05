package com.afterqcd.study.flink.dataflow

import java.util.Date

import com.afterqcd.study.flink.model.{Text, WordCount}
import com.afterqcd.study.flink.utils.ThrottledIterator
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by afterqcd on 2017/4/1.
  */
class IngestionTimeTest extends FlatSpec with Matchers {
  import com.afterqcd.study.flink.utils.DateUtils.now

  private val textSource = List(
    Text("hello world", now),
    Text("hello flink", now),
    Text("hello spark", now),
    Text("hello kafka", now),
    Text("hello flink", now),
    Text("hello spark", now),
    Text("hello kafka", now),
    Text("hello flink", now)
  )

  "Flink" should "support ingestion time" in {
    implicit val stringTypeInfo = TypeInformation.of(classOf[String])
    implicit val textTypeInfo = TypeInformation.of(classOf[Text])
    implicit val wordCountTypeInfo = TypeInformation.of(classOf[WordCount])

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime)
    env.setParallelism(1)

    val texts = env.fromCollection(ThrottledIterator(textSource, 20))
    texts.flatMap(_.text.split("\\s"))
      .map(WordCount(_, 1))
      .keyBy(_.word)
      .timeWindow(Time.milliseconds(100))
      .reduce(_ + _)
      .print()

    env.execute()
  }
}
