package com.afterqcd.study.serde.evolution.avro

import java.io.{ByteArrayOutputStream, EOFException, File}

import com.afterqcd.study.serde.avro.LogEntry
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

/**
  * Created by afterqcd on 2016/11/7.
  */
class SpecificDatumTest extends FlatSpec with Matchers {
  val entry1 = LogEntry.newBuilder().setName("Jeff").setResource("readme.txt").setIp("192.168.1.1").build()
  val entry2 = LogEntry.newBuilder().setName("John").setResource("readme.md").setIp("192.168.1.2").build()

  "Avro" should "支持序列化到文件并读回" in {
    val datumWriter = new SpecificDatumWriter(classOf[LogEntry])
    val fileWriter = new DataFileWriter(datumWriter)
    val file = new File("/tmp/log")
    fileWriter.create(entry1.getSchema, file)

    fileWriter.append(entry1)
    fileWriter.append(entry2)

    fileWriter.close()

    val datumReader = new SpecificDatumReader[LogEntry]
    val fileReader = new DataFileReader[LogEntry](file, datumReader)

    fileReader.iterator().asScala.toSeq should contain theSameElementsInOrderAs Seq(entry1, entry2)

    fileReader.close()

    file.delete
  }

  it should "支持序列化到字节数组并读回" in {
    val datumWriter = new SpecificDatumWriter(classOf[LogEntry])
    val os = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get().binaryEncoder(os, null)

    datumWriter.write(entry1, encoder)
    datumWriter.write(entry2, encoder)

    encoder.flush()

    val bytes = os.toByteArray

    val decoder = DecoderFactory.get().binaryDecoder(bytes, null)
    val datumReader = new SpecificDatumReader(classOf[LogEntry])

    val entries = ArrayBuffer.empty[LogEntry]

    try {
      while (true) {
        entries += datumReader.read(null, decoder)
      }
    } catch {
      case e: EOFException =>
    }

    entries should contain theSameElementsInOrderAs Seq(entry1, entry2)
  }
}
