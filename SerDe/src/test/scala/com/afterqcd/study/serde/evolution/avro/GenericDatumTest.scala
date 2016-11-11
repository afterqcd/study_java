package com.afterqcd.study.serde.evolution.avro

import java.io.File

import org.apache.avro.Schema
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.generic.{GenericData, GenericDatumReader, GenericDatumWriter, GenericRecord}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.collection.JavaConverters._

/**
  * Created by afterqcd on 2016/11/7.
  */
class GenericDatumTest extends FlatSpec with Matchers with BeforeAndAfterAll {

  val SchemaString = """{
                            "namespace": "com.afterqcd.study.serde.avro",
                            "name": "LogEntry",
                            "type": "record",
                            "fields": [
                                {"name": "name", "type": "string"},
                                {"name": "resource", "type": ["string", "null"], "default": null},
                                {"name": "ip", "type": ["string", "null"], "default": null}
                            ]
                        }"""

  val schema = new Schema.Parser().parse(SchemaString)

  val entry1 = new GenericData.Record(schema)
  entry1.put("name", "Jeff")
  entry1.put("resource", "readme.txt")
  entry1.put("ip", "192.168.1.1")

  val entry2 = new GenericData.Record(schema)
  entry2.put("name", "John")
  entry2.put("resource", "readme.md")
  entry2.put("ip", "192.168.1.2")

  "Avro" should "支持序列化到文件并读回" in {
    val datumWriter = new GenericDatumWriter[GenericRecord](schema)
    val fileWriter = new DataFileWriter(datumWriter)
    val file = new File("/tmp/log")
    fileWriter.create(schema, file)
    fileWriter.append(entry1)
    fileWriter.append(entry2)

    fileWriter.close()

    val datumReader = new GenericDatumReader[Any](schema)
    val fileReader = new DataFileReader(file, datumReader)
    fileReader.iterator().asScala.toSeq should contain theSameElementsInOrderAs Seq(entry1, entry2)

    fileReader.close()
  }
}
