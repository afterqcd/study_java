package com.afterqcd.study.kafka.protobuf

import java.util

import com.google.protobuf.Parser
import org.apache.kafka.common.serialization.Deserializer

/**
  * Created by afterqcd on 2016/11/30.
  */
class ProtobufDeserializer extends Deserializer[AnyRef] {
  var parser: Parser[_] = _

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
    val confName = if (isKey) "key.class" else "value.class"
    val messageClz = configs.get(confName).toString
    this.parser = parserFor(Class.forName(messageClz))
  }

  private def parserFor(clz: Class[_]): Parser[_] = {
    try {
      val getParserForType = clz.getMethod("parser")
      getParserForType.invoke(null).asInstanceOf[Parser[_]]
    } catch {
      case e: Throwable =>
        throw new IllegalArgumentException(s"Failed to extract protobuf message parser from ${clz.getName}", e)
    }
  }

  override def close(): Unit = {
    // nothing to do
  }

  override def deserialize(topic: String, data: Array[Byte]): AnyRef = {
    parser.parseFrom(data).asInstanceOf[AnyRef]
  }
}
