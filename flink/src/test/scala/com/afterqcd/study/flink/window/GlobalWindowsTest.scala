//package com.afterqcd.study.flink.window
//
//import com.afterqcd.study.flink.model.{Text, WordCount, WordCountWithTime}
//import com.afterqcd.study.flink.utils.DateUtils.{format, parse}
//import com.afterqcd.study.flink.utils.{ThrottledIterator, Watermark}
//import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.util.Collector
//import org.apache.flink.api.scala._
//import org.scalatest.FlatSpec
//
///**
//  * Created by afterqcd on 2017/4/8.
//  */
//class GlobalWindowsTest extends FlatSpec {
//  private val textSource = List(
//    Text("hello", parse("10:30:15")),
//    Text("hello world", parse("10:30:16")),
//    Text("flink", parse("10:30:19")),
//    Text("flink aha", parse("10:30:20")),
//    Text("spark", parse("10:20:10")),
//    Text("ooo jjj", parse("10:30:25")),
//    Text("spark aha", parse("10:20:11")),
//    Text("spark aha", parse("10:20:11")),
//    Text("spark aha", parse("10:20:11"))
//  )
//
//  "Flink" should "support global window" in {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    env.setParallelism(1)
//
//    val texts = env.fromCollection(ThrottledIterator(textSource, 1))
//      .assignTimestampsAndWatermarks(Watermark.outOfOrderness[Text](Time.seconds(2))(_.time))
//
//    texts.flatMap(_.text.split("\\s"))
//      .keyBy(word => word)
//      .process(new CountWithTimeout)
//      .addSink(wc => println(s"${wc.wordCount} @ ${format(wc.time)} @ ${Thread.currentThread().getId}"))
//
//    env.execute()
//  }
//}
//
//class CountWithTimeout extends RichPro[String, WordCountWithTime] {
//  lazy val wcState: ValueState[WordCountWithTime] = getRuntimeContext
//    .getState(new ValueStateDescriptor[WordCountWithTime]("count", classOf[WordCountWithTime]))
//
//  override def processElement(value: String, ctx: Context, out: Collector[WordCountWithTime]): Unit = {
//    val now = ctx.timestamp()
//    val watermark = ctx.timerService().currentWatermark()
//    println(s"$value @ ${format(now)} watermark ${format(watermark)} @ ${Thread.currentThread().getId}")
//
//    val wc = wcState.value() match {
//      case null => WordCountWithTime(WordCount(value, 1), ctx.timestamp())
//      case WordCountWithTime(existedWc, _) => WordCountWithTime(WordCount(value, existedWc.count + 1), ctx.timestamp())
//    }
//
//    wcState.update(wc)
//    ctx.timerService()
//    ctx.timerService().registerEventTimeTimer(ctx.timestamp() + 2000)
//  }
//
//  override def onTimer(now: Long, ctx: OnTimerContext, out: Collector[WordCountWithTime]): Unit = {
//    val watermark = ctx.timerService().currentWatermark()
//    println(s"onTimer @ ${format(now)} watermark ${format(watermark)} @ ${Thread.currentThread().getId}")
//    wcState.value() match {
//      case WordCountWithTime(_, lastModified) if lastModified + 2000 == now =>
//        out.collect(wcState.value())
//        wcState.clear()
//      case _ =>
//    }
//  }
//}
