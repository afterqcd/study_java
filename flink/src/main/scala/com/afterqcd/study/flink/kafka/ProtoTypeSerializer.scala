package com.afterqcd.study.flink.kafka

import com.google.protobuf.{GeneratedMessageV3, Parser}
import org.apache.flink.api.common.typeutils.{CompatibilityResult, ParameterlessTypeSerializerConfig, TypeSerializer, TypeSerializerConfigSnapshot}
import org.apache.flink.core.memory.{DataInputView, DataOutputView}

/**
  * Created by afterqcd on 2017/4/14.
  */
class ProtoTypeSerializer[T <: GeneratedMessageV3](val clz: Class[T]) extends TypeSerializer[T] {
  @transient private lazy val parser = clz.getMethod("parser").invoke(null).asInstanceOf[Parser[T]]
  @transient private lazy val defaultInstanceGetter = clz.getMethod("getDefaultInstance")

  override def createInstance(): T = defaultInstanceGetter.invoke(null).asInstanceOf[T]

  override def getLength: Int = -1

  override def canEqual(obj: scala.Any): Boolean = obj.isInstanceOf[ProtoTypeSerializer[T]]

  override def copy(from: T): T = from.toBuilder.build().asInstanceOf[T]

  override def copy(from: T, reuse: T): T = copy(from)

  override def copy(source: DataInputView, target: DataOutputView): Unit =
    serialize(deserialize(source), target)

  override def serialize(record: T, target: DataOutputView): Unit = {
    println(s"Serialize $record")
    target.writeInt(record.getSerializedSize)
    target.write(record.toByteArray)
  }

  override def isImmutableType: Boolean = true

  override def duplicate(): TypeSerializer[T] = new ProtoTypeSerializer(clz)

  override def deserialize(source: DataInputView): T = {
    val size = source.readInt()
    val bytes = new Array[Byte](size)
    source.readFully(bytes)
    println(s"deserialize $bytes")
    parser.parseFrom(bytes)
  }

  override def deserialize(reuse: T, source: DataInputView): T = deserialize(source)

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case serializer: ProtoTypeSerializer[T] =>
        serializer.clz.equals(this.clz)
      case _ => false
    }
  }

  override def hashCode(): Int = clz.hashCode()

  override def toString: String = s"ProtoTypeSerializer for class ${clz.getName}"

  override def ensureCompatibility(typeSerializerConfigSnapshot: TypeSerializerConfigSnapshot): CompatibilityResult[T] =
    CompatibilityResult.compatible()

  override def snapshotConfiguration(): TypeSerializerConfigSnapshot = new ParameterlessTypeSerializerConfig()
}
