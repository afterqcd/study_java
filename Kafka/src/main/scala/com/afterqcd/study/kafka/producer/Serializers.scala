package com.afterqcd.study.kafka.producer

import com.google.protobuf.MessageLite
import org.apache.kafka.common.serialization._

/**
  * Created by afterqcd on 2016/11/29.
  */
object Serializers {
  private val kafkaPredefinedSerializers = Map[Class[_], Class[_]](
    classOf[String] -> classOf[StringSerializer],
    classOf[Array[Byte]] -> classOf[ByteArraySerializer],

    classOf[java.lang.Long] -> classOf[LongSerializer],
    classOf[java.lang.Double] -> classOf[DoubleSerializer],
    classOf[java.lang.Integer] -> classOf[IntegerSerializer],

    classOf[Long] -> classOf[LongSerializer],
    classOf[Double] -> classOf[DoubleSerializer],
    classOf[Int] -> classOf[IntegerSerializer]
  ).mapValues(_.getName)

  /**
    * Get full name of serializer for specified class.
    * @param clz
    * @tparam T
    * @return
    */
  def serializerFor[T](clz: Class[T]): String = {
    if (kafkaPredefinedSerializers.contains(clz)) {
      kafkaPredefinedSerializers(clz)
    } else if (classOf[MessageLite].isAssignableFrom(clz)) {
      "com.afterqcd.study.kafka.protobuf.ProtobufSerializer"
    } else {
      throw new IllegalArgumentException(s"${clz.getName} is not an supported key or value type")
    }
  }
}
