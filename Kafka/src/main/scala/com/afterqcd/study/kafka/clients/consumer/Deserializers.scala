package com.afterqcd.study.kafka.clients.consumer

import com.afterqcd.study.kafka.protobuf.ProtobufDeserializer
import com.google.protobuf.MessageLite
import org.apache.kafka.common.serialization._

/**
  * Created by afterqcd on 2016/12/1.
  */
object Deserializers {
  private val kafkaPredefinedDeserializers = Map[Class[_], Class[_]](
    classOf[String] -> classOf[StringDeserializer],
    classOf[Array[Byte]] -> classOf[ByteArrayDeserializer],

    classOf[java.lang.Long] -> classOf[LongDeserializer],
    classOf[java.lang.Double] -> classOf[DoubleDeserializer],
    classOf[java.lang.Integer] -> classOf[IntegerDeserializer],

    classOf[Long] -> classOf[LongDeserializer],
    classOf[Double] -> classOf[DoubleDeserializer],
    classOf[Int] -> classOf[IntegerDeserializer]
  ).mapValues(_.getName)

  /**
    * Get full name of serializer for specified class.
    * @param clz
    * @tparam T
    * @return
    */
  def deserializerFor[T](clz: Class[T]): String = {
    if (kafkaPredefinedDeserializers.contains(clz)) {
      kafkaPredefinedDeserializers(clz)
    } else if (classOf[MessageLite].isAssignableFrom(clz)) {
      classOf[ProtobufDeserializer].getName
    } else {
      throw new IllegalArgumentException(s"${clz.getName} is not an supported key or value type")
    }
  }
}
