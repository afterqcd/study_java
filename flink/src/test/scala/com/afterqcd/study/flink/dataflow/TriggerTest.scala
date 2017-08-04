package com.afterqcd.study.flink.dataflow

import com.afterqcd.study.flink.model.{Text, WordCount, WordCountWithTime}
import com.afterqcd.study.flink.utils.Watermark
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.{ContinuousEventTimeTrigger, EventTimeTrigger, Trigger}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._
import org.scalatest.FlatSpec

/**
  * Created by afterqcd on 2017/4/2.
  */
class TriggerTest extends FlatSpec {

  import com.afterqcd.study.flink.utils.DateUtils._

  private val textSource = List(
    Text("hello world", parse("10:30:10")),
    Text("hello flink", parse("10:30:10")),
    Text("hello spark", parse("10:30:12")),
    Text("hello kafka", parse("10:30:13")),
    Text("hello flink", parse("10:30:13")),

    Text("hello event", parse("10:30:15")),
    Text("hello spark", parse("10:30:13")),
    Text("hello kafka", parse("10:30:13")),
    Text("hello spark", parse("10:30:14")),
    Text("hello kafka", parse("10:30:14")),

    Text("hello event", parse("10:30:16")),
    Text("hello lateness1", parse("10:30:14")),
    Text("hello lateness2", parse("10:30:14"))
  )

  "Flink" should "support fire once at watermark" in {
    wordCountWithTrigger(EventTimeTrigger.create())
  }

  it should "support fire interval before watermark" in {
    wordCountWithTrigger(ContinuousEventTimeTrigger.of(Time.seconds(1)))
  }

  private def wordCountWithTrigger(trigger: Trigger[_ >: WordCount, _ >: TimeWindow]) = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val texts = env.fromCollection(textSource)
      .assignTimestampsAndWatermarks {
        Watermark.outOfOrderness[Text](Time.seconds(1))(_.time) // 可预计的无序程度
      }

    val wordCounts = texts.flatMap(_.text.split("\\s"))
      .map(WordCount(_, 1))
      .keyBy(_.word)
      .timeWindow(Time.seconds(5))
      .allowedLateness(Time.seconds(1)) // 相比于水位线的最大允许延迟
      .trigger(trigger)
      .reduce(
        _ + _,
        (_, window, wcs, out: Collector[WordCountWithTime]) =>
          out.collect(WordCountWithTime(wcs.iterator.next(), window.getStart))
      )

    wordCounts.addSink(wc => println(s"${wc.wordCount} @ ${format(wc.time)}"))

    env.execute()
  }
}
