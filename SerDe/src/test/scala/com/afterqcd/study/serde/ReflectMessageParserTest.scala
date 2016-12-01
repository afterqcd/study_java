package com.afterqcd.study.serde

import com.afterqcd.study.serde.protobuf.LogEntryOuterClass
import com.google.protobuf.Parser
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by afterqcd on 2016/11/29.
  */
class ReflectMessageParserTest extends FlatSpec with Matchers {
  "Reflect" should "create parser" in {
    val bytes = LogEntryOuterClass.LogEntry.newBuilder().setIp("127.0.0.1").build().toByteArray
    val obj = parserFor(Class.forName("com.afterqcd.study.serde.protobuf.LogEntryOuterClass$LogEntry")).parseFrom(bytes)
    obj.asInstanceOf[LogEntryOuterClass.LogEntry].getIp should be ("127.0.0.1")
  }

  private def parserFor[T](clz: Class[T]): Parser[T] = {
    val getParserForType = clz.getMethod("parser")
    getParserForType.invoke(null).asInstanceOf[Parser[T]]
  }
}
