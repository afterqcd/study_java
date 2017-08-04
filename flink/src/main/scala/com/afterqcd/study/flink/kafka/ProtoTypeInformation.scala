package com.afterqcd.study.flink.kafka

import com.google.protobuf.GeneratedMessageV3
import org.apache.flink.api.common.ExecutionConfig
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.common.typeutils.TypeSerializer

/**
  * Created by afterqcd on 2017/4/14.
  */
class ProtoTypeInformation[T <: GeneratedMessageV3](val clz: Class[T]) extends TypeInformation {
  println(s"create type information for ${clz.getName}")

  override def isBasicType: Boolean = false

  override def getTotalFields: Int = 1

  override def canEqual(obj: scala.Any): Boolean = obj.isInstanceOf[ProtoTypeInformation[T]]

  override def getArity: Int = 1

  override def isKeyType: Boolean = false

  override def getTypeClass: Class[Nothing] = clz.asInstanceOf[Class[Nothing]]

  override def isTupleType: Boolean = false

  override def createSerializer(config: ExecutionConfig): TypeSerializer[Nothing] = {
    println(s"create type serializer for ${clz.getName}")
    new ProtoTypeSerializer(clz).asInstanceOf[TypeSerializer[Nothing]]
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case info: ProtoTypeInformation[T] =>
        info.clz.equals(this.clz)
      case _ => false
    }
  }

  override def hashCode(): Int = clz.hashCode()

  override def toString: String = s"ProtoTypeInformation for class ${clz.getName}"
}
