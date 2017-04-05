package com.afterqcd.study.flink.dataflow

import com.afterqcd.study.flink.model.{Text, WordCount, WordCountWithTime}
import com.afterqcd.study.flink.utils.{ThrottledIterator, Watermark}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
import org.scalatest.FlatSpec

/**
  * Created by afterqcd on 2017/4/2.
  */
class WatermarkTest extends FlatSpec {

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
    Text("hello flink", parse("10:30:15"))
  )

  "Flink" should "supports ascending timestamp" in {
    wordCountWithWatermark {
      _.assignAscendingTimestamps(_.time)
    }
  }

  it should "supports bounded out-of-orders timestamp" in {
    wordCountWithWatermark {
      _.assignTimestampsAndWatermarks(
        Watermark.outOfOrderness[Text](Time.seconds(2))(_.time)
      )
    }
  }

  private def wordCountWithWatermark(assigner: DataStream[Text] => DataStream[Text]): Unit = {
    implicit val stringTypeInfo = TypeInformation.of(classOf[String])
    implicit val textTypeInfo = TypeInformation.of(classOf[Text])
    implicit val wordCountTypeInfo = TypeInformation.of(classOf[WordCount])
    implicit val wordCountWithTimeTypeInfo = TypeInformation.of(classOf[WordCountWithTime])

    val start = now

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val texts = assigner(env.fromCollection(ThrottledIterator(textSource, 1)))

    val wordCounts = texts.flatMap(_.text.split("\\s"))
      .map(WordCount(_, 1))
      .keyBy(_.word)
      .timeWindow(Time.seconds(1))
      .reduce(
        _ + _,
        (_, window, wcs, out: Collector[WordCountWithTime]) =>
          out.collect(WordCountWithTime(wcs.iterator.next(), window.getStart))
      )

    wordCounts.addSink(wc => println(s"${wc.wordCount} @ ${format(wc.time)} and elapsed ${now - start}ms"))

    env.execute()
  }
}