package com.afterqcd.study.kafka.protobuf

import com.afterqcd.study.kafka.model.LogEntryOuterClass
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by afterqcd on 2016/11/30.
  */
class ProtobufSerializerTest extends FlatSpec with Matchers {
  "ProtobufSerializer" should "支持序列化Protobuf对象" in {
    val logEntry = LogEntryOuterClass.LogEntry.newBuilder().setIp("127.0.0.1").build()
    val bytes = new ProtobufSerializer().serialize(null, logEntry)
    bytes.length should be > 0
  }
}
