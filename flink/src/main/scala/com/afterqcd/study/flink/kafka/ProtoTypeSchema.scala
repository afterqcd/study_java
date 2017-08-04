package com.afterqcd.study.flink.kafka

import com.afterqcd.study.flink.Dto
import com.google.protobuf.{AbstractMessage, GeneratedMessageV3, Parser}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.util.serialization.{DeserializationSchema, SerializationSchema}

/**
  * Created by afterqcd on 2017/4/14.
  */
class ProtoTypeSchema[T <: GeneratedMessageV3](val clz: Class[T])
  extends DeserializationSchema[T] with SerializationSchema[T] {
  @transient private lazy val parser =
    clz.getMethod("parser").invoke(null).asInstanceOf[Parser[T]]

  override def isEndOfStream(t: T): Boolean = false

  override def deserialize(bytes: Array[Byte]): T = {
    parser.parseFrom(bytes)
  }

  override def serialize(t: T): Array[Byte] = {
    t.toByteArray
  }

  override def getProducedType: TypeInformation[T] =
    new ProtoTypeInformation(clz).asInstanceOf[TypeInformation[T]]
}
