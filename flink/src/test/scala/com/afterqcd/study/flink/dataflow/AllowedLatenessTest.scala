package com.afterqcd.study.flink.dataflow

import com.afterqcd.study.flink.model.{Text, WordCount, WordCountWithTime}
import com.afterqcd.study.flink.utils.{ThrottledIterator, Watermark}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._
import org.scalatest.FlatSpec

/**
  * Created by afterqcd on 2017/4/2.
  */
class AllowedLatenessTest extends FlatSpec {
  import com.afterqcd.study.flink.utils.DateUtils._

  private val textSource = List(
    Text("hello world", parse("10:30:15")),
    Text("hello flink", parse("10:30:15")),
    Text("hello spark", parse("10:30:17")),
    Text("hello kafka", parse("10:30:17")),
    Text("hello flink", parse("10:30:18")),
    Text("hello event", parse("10:30:16")),
    Text("hello spark", parse("10:30:18")),
    Text("hello kafka", parse("10:30:16")),
    Text("hello lateness", parse("10:30:15"))
  )

  "Flink" should "support allowed lateness" in {
    val start = now

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val texts = env.fromCollection(ThrottledIterator(textSource, 1))
      .assignTimestampsAndWatermarks {
        Watermark.outOfOrderness[Text](Time.seconds(2))(_.time) // 可预计的无序程度
      }

    val wordCounts = texts.flatMap(_.text.split("\\s"))
      .map(WordCount(_, 1))
      .keyBy(_.word)
      .timeWindow(Time.seconds(1))
      .allowedLateness(Time.seconds(1)) // 相比于水位线的最大允许延迟
      .reduce(
        _ + _,
        (_, window, wcs, out: Collector[WordCountWithTime]) =>
          out.collect(WordCountWithTime(wcs.iterator.next(), window.getStart))
      )

    wordCounts.addSink(wc => println(s"${wc.wordCount} @ ${format(wc.time)} and elapsed ${now - start}ms"))

    env.execute()
  }
}
