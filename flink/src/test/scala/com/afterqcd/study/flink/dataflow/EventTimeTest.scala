package com.afterqcd.study.flink.dataflow

import com.afterqcd.study.flink.model.{Text, WordCount, WordCountWithTime}
import com.afterqcd.study.flink.utils.ThrottledIterator
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by afterqcd on 2017/4/1.
  */
class EventTimeTest extends FlatSpec with Matchers {

  import com.afterqcd.study.flink.utils.DateUtils._

  private val textSource = List(
    Text("hello world", parse("10:30:15")),
    Text("hello flink", parse("10:30:15")),
    Text("hello spark", parse("10:30:16")),
    Text("hello kafka", parse("10:30:16")),
    Text("hello flink", parse("10:30:17")),
    Text("hello event", parse("10:30:17")),
    Text("hello spark", parse("10:30:18")),
    Text("hello kafka", parse("10:30:18")),
    Text("hello flink", parse("10:30:19"))
  )

  "Flink" should "support event time " in {
    implicit val stringTypeInfo = TypeInformation.of(classOf[String])
    implicit val textTypeInfo = TypeInformation.of(classOf[Text])
    implicit val wordCountTypeInfo = TypeInformation.of(classOf[WordCount])
    implicit val wordCountWithTimeTypeInfo = TypeInformation.of(classOf[WordCountWithTime])

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val texts = env.fromCollection(ThrottledIterator(textSource, 1))
      .assignAscendingTimestamps(_.time)

    val wordCounts = texts.flatMap(_.text.split("\\s"))
      .map(WordCount(_, 1))
      .keyBy(_.word)
      .window(TumblingEventTimeWindows.of(Time.seconds(1)))
      .reduce(
        _ + _,
        (_, window, wcs, out: Collector[WordCountWithTime]) =>
          out.collect(WordCountWithTime(wcs.iterator.next(), window.getStart))
      )

    wordCounts.addSink(wc => println(s"${wc.wordCount} @ ${format(wc.time)}"))

    env.execute()
  }
}
