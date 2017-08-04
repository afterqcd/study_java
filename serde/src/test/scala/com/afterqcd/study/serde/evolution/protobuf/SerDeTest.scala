package com.afterqcd.study.serde.evolution.protobuf

import java.io.{ByteArrayOutputStream, OutputStream}
import java.nio.ByteBuffer

import com.afterqcd.study.serde.protobuf.LogEntryOuterClass
import com.google.protobuf._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by afterqcd on 2016/11/7.
  */
class SerDeTest extends FlatSpec with Matchers {
  "Protobuf" should "支持单个消息的序列化与反序列化" in {
    val entry = LogEntryOuterClass.LogEntry.newBuilder()
      .setName("John").setResource("readme.txt").setIp("192.168.1.1")
      .build()

    val os = new ByteArrayOutputStream()
    entry.writeTo(os)
    entry.getSerializedSize

    val bytes = os.toByteArray

    val entryDeserialized = LogEntryOuterClass.LogEntry.parseFrom(bytes)
    entryDeserialized should be(entry)
  }

  it should "支持多个消息的序列化与反序列化" in {
    val entry1 = LogEntryOuterClass.LogEntry.newBuilder()
      .setName("John").setResource("readme.txt").setIp("192.168.1.1")
      .build()

    val entry2 = LogEntryOuterClass.LogEntry.newBuilder()
      .setName("Som").setResource("readme.txt").setIp("192.168.1.2")
      .build()

    val os = new ByteArrayOutputStream()
    writeNext(entry1, os)
    writeNext(entry2, os)

    val bytes = os.toByteArray

    val byteBuffer = ByteBuffer.wrap(bytes)
    val parser: Array[Byte] => LogEntryOuterClass.LogEntry = LogEntryOuterClass.LogEntry.parseFrom
    readNext(byteBuffer, parser) should be (entry1)
    readNext(byteBuffer, parser) should be (entry2)
  }

  private def writeNext[T <: GeneratedMessageV3](message: T, os: OutputStream): Unit = {
    os.write(ByteBuffer.allocate(4).putInt(message.getSerializedSize).array())
    message.writeTo(os)
  }

  private def readNext[T <: GeneratedMessageV3](byteBuffer: ByteBuffer, parser: Array[Byte] => T): T = {
    val messageLength = byteBuffer.getInt()
    val messageBytes = new Array[Byte](messageLength)
    byteBuffer.get(messageBytes)
    parser(messageBytes)
  }
}
