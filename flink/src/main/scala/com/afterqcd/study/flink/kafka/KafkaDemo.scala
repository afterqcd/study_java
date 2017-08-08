package com.afterqcd.study.flink.kafka

import java.util.Properties

import com.afterqcd.study.flink.Dto
import org.apache.flink.api.scala._
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.partitioner.KafkaPartitioner
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer010, FlinkKafkaProducer010}
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

/**
  * Created by afterqcd on 2017/4/13.
  */
object KafkaDemo {
  def main(args: Array[String]): Unit = {
    implicit val customerTypeInfo = new ProtoTypeInformation(classOf[Dto.Customer])
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.enableCheckpointing(3000)
    env.getCheckpointConfig
    env.setStateBackend(new RocksDBStateBackend("hdfs://shuyou01:8020/flink/checkpoints"))

    env.addSource(inputTopicConsumer)
      .map(str => Dto.Customer.newBuilder().setName(str).setAge(str.length).build())
      .keyBy(_.getName)
      .reduce((c1, _) => c1)
      .addSink(outputTopicProducer)

    env.addSource(outputTopicConsumer).print()

    env.execute()
  }

  private def inputTopicConsumer = {
    val consumerProps = new Properties()
    consumerProps.setProperty("bootstrap.servers", "172.16.185.249:9092")
    consumerProps.setProperty("group.id", "test")
    consumerProps.setProperty("auto.offset.reset", "earliest")

    val consumer = new FlinkKafkaConsumer010[String](
      "input.topic", new SimpleStringSchema, consumerProps
    )

    consumer
  }

  private def outputTopicProducer = {
    val producerProps = new Properties()
    producerProps.setProperty("bootstrap.servers", "172.16.185.249:9092")

    val partitioner = new KafkaPartitioner[Dto.Customer] {

      override def open(parallelInstanceId: Int, parallelInstances: Int, partitions: Array[Int]): Unit = {
        println(s"parallel instance id $parallelInstanceId, parallel instances $parallelInstances, kafka partitions ${partitions.length}")
      }

      override def partition(next: Dto.Customer, serializedKey: Array[Byte],
                             serializedValue: Array[Byte], numPartitions: Int): Int = {
        next.getName.hashCode % numPartitions
      }
    }

    val producer = new FlinkKafkaProducer010[Dto.Customer](
      "output.topic", new ProtoTypeSchema(classOf[Dto.Customer]), producerProps, partitioner
    )
    producer.setLogFailuresOnly(false)
    producer.setFlushOnCheckpoint(true)

    producer
  }

  private def outputTopicConsumer = {
    val consumerProps = new Properties()
    consumerProps.setProperty("bootstrap.servers", "172.16.185.249:9092")
    consumerProps.setProperty("group.id", "test")
    consumerProps.setProperty("auto.offset.reset", "earliest")

    val consumer = new FlinkKafkaConsumer010[Dto.Customer](
      "output.topic", new ProtoTypeSchema(classOf[Dto.Customer]), consumerProps
    )

    consumer
  }
}
