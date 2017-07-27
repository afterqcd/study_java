package com.afterqcd.study.kafka.protobuf

import com.afterqcd.study.kafka.model.LogEntryOuterClass
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/11/30.
  */
class ProtobufDeserializerTest extends FlatSpec with Matchers {
  "ProtobufDeserializer" should "支持反序列化Protobuf对象" in {
    val logEntry = LogEntryOuterClass.LogEntry.newBuilder().setIp("127.0.0.1").build()
    val bytes = new ProtobufSerializer().serialize(null, logEntry)

    val deserializer = new ProtobufDeserializer()
    deserializer.configure(
      Map("value.class" -> "com.afterqcd.study.kafka.model.LogEntryOuterClass$LogEntry").asJava,
      isKey = false
    )

    val logEntry2 = deserializer.deserialize(null, bytes).asInstanceOf[LogEntryOuterClass.LogEntry]
    logEntry2.getIp should be ("127.0.0.1")
  }
}
