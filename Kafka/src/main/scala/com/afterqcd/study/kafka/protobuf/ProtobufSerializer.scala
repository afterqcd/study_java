package com.afterqcd.study.kafka.protobuf

import java.util

import com.google.protobuf.MessageLite
import org.apache.kafka.common.serialization.Serializer

/**
  * Created by afterqcd on 2016/11/29.
  */
class ProtobufSerializer extends Serializer[AnyRef] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
    // nothing to do
  }

  override def serialize(topic: String, data: AnyRef): Array[Byte] = {
    data match {
      case message: MessageLite =>
        message.toByteArray
      case _ =>
        throw new IllegalArgumentException(
          s"Class ${data.getClass.getName} is not derived from com.google.protobuf.MessageLite"
        )
    }
  }

  override def close(): Unit = {
    // nothing to do
  }
}
