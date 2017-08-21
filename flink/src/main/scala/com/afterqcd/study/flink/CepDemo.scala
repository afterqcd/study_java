package com.afterqcd.study.flink
import org.apache.flink.api.scala._
import org.apache.flink.cep.scala.CEP
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object CepDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val temperatureStream = env.fromElements(
      TemperatureEvent(1, 30f),
      TemperatureEvent(1, 35f),
      TemperatureEvent(1, 32f),
      TemperatureEvent(2, 30f),
      TemperatureEvent(2, 35f),
      TemperatureEvent(2, 34f),
      TemperatureEvent(2, 35f)
    )

    val warningPattern = Pattern
      .begin[TemperatureEvent]("first").where(_.temp > 33)
      .next("second").where(_.temp > 33)
    val warningStream = CEP.pattern(temperatureStream.keyBy(_.rackId), warningPattern)
      .select(map => {
        val first = map.get("first").map(_.head).get
        val second = map.get("second").map(_.head).get
        TemperatureWarning(first.rackId, (first.temp + second.temp) / 2)
      })

    warningStream.print()
    env.execute("Temperature Monitor")
  }

  case class TemperatureEvent(rackId: Int, temp: Float)
  case class TemperatureWarning(rackId: Int, temp: Float)
}
