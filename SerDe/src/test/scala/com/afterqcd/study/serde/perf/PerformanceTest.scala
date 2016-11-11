package com.afterqcd.study.serde.perf

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.util.concurrent.{CountDownLatch, Executors}

import com.afterqcd.study.serde.avro.LogEntry
import com.afterqcd.study.serde.java.{LogEntry1, LogEntry2}
import com.afterqcd.study.serde.protobuf.LogEntryOuterClass
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by afterqcd on 2016/11/7.
  */
class PerformanceTest extends FlatSpec with Matchers {
  val ThreadCount = 1
  val LoopPerThread = 10000

  "Avro" should "尽可能快地序列化" in {
    elapsed("Serialize by Avro-SpecificDatum") {
      duplicateInThreads(ThreadCount) {
        val entry = LogEntry.newBuilder()
          .setName("Jeff").setResource("readme.txt").setIp("192.168.1.1")
          .build()

        val datumWriter = new SpecificDatumWriter(classOf[LogEntry])
        val os = new ByteArrayOutputStream()
        val encoder = EncoderFactory.get().binaryEncoder(os, null)

        1 to LoopPerThread foreach { _ =>
          datumWriter.write(entry, encoder)
          encoder.flush()
          os.reset()
        }
      }
    }
  }

  it should "尽可能快地反序列化" in {
    elapsed("Deserialize by Avro-SpecificDatum") {
      duplicateInThreads(ThreadCount) {
        val entry = LogEntry.newBuilder()
          .setName("Jeff").setResource("readme.txt").setIp("192.168.1.1")
          .build()

        val datumWriter = new SpecificDatumWriter(classOf[LogEntry])
        val os = new ByteArrayOutputStream()
        val encoder = EncoderFactory.get().binaryEncoder(os, null)

        datumWriter.write(entry, encoder)
        encoder.flush()

        val bytes = os.toByteArray
//        println(s"${bytes.length} bytes by Avro-SpecificDatum")

        val is = new ByteArrayInputStream(bytes)
        val datumReader = new SpecificDatumReader(classOf[LogEntry])
        val decoder = DecoderFactory.get().binaryDecoder(is, null)

        1 to LoopPerThread foreach { _ =>
          datumReader.read(null, decoder)
          is.reset()
        }
      }
    }
  }

  "Protobuf" should "尽可能快地序列化" in {
    elapsed("Serialize by Protobuf") {
      duplicateInThreads(ThreadCount) {
        LogEntryOuterClass.LogEntry.getDescriptor
        val message = LogEntryOuterClass.LogEntry.newBuilder()
          .setName("Jeff").setResource("readme.txt").setIp("192.168.1.1")
          .build()
        val os = new ByteArrayOutputStream()

        1 to LoopPerThread foreach { _ =>
          message.writeTo(os)
          os.reset()
        }
      }
    }
  }

  it should "尽可能快地反序列化" in {
    elapsed("Deserialize by Protobuf") {
      duplicateInThreads(ThreadCount) {
        val message = LogEntryOuterClass.LogEntry.newBuilder()
          .setName("Jeff").setResource("readme.txt").setIp("192.168.1.1")
          .build()
        val os = new ByteArrayOutputStream()
        message.writeTo(os)

        val bytes = os.toByteArray
//        println(s"${bytes.length} bytes by Protobuf")

        1 to LoopPerThread foreach { _ =>
          LogEntryOuterClass.LogEntry.parseFrom(bytes)
        }
      }
    }
  }

  "Externalizable" should "尽可能快地序列化" in {
    elapsed("Serialize by Java-Externalizable") {
      duplicateInThreads(ThreadCount) {
        val message = new LogEntry2()
        message.setName("Jeff")
        message.setResource("readme.txt")
        message.setIp("192.168.1.1")

        val oos = new ObjectOutputStream(new ByteArrayOutputStream())

        1 to LoopPerThread foreach { _ =>
          oos.writeObject(message)
          oos.reset()
        }
      }
    }
  }

  it should "尽可能快地反序列化" in {
    elapsed("Deserialize by Java-Externalizable") {
      duplicateInThreads(ThreadCount) {
        val message = new LogEntry2()
        message.setName("Jeff")
        message.setResource("readme.txt")
        message.setIp("192.168.1.1")

        val os = new ByteArrayOutputStream()
        val oos = new ObjectOutputStream(os)
        oos.writeObject(message)
//        println(s"${os.toByteArray.length} bytes by Java-Externalizable")
        val is = new ByteArrayInputStream(os.toByteArray)

        1 to LoopPerThread foreach { _ =>
          new ObjectInputStream(is).readObject()
          is.reset()
        }
      }
    }
  }

  "Serializable" should "尽可能快地序列化" in {
    elapsed("Serialize by Java-Serializable") {
      duplicateInThreads(ThreadCount) {
        val message = new LogEntry1()
        message.setName("Jeff")
        message.setResource("readme.txt")
        message.setIp("192.168.1.1")

        val oos = new ObjectOutputStream(new ByteArrayOutputStream())

        1 to LoopPerThread foreach { _ =>
          oos.writeObject(message)
          oos.reset()
        }
      }
    }
  }

  it should "尽可能快地反序列化" in {
    elapsed("Deserialize by Java-Serializable") {
      duplicateInThreads(ThreadCount) {
        val message = new LogEntry1()
        message.setName("Jeff")
        message.setResource("readme.txt")
        message.setIp("192.168.1.1")

        val os = new ByteArrayOutputStream()
        val oos = new ObjectOutputStream(os)
        oos.writeObject(message)
//        println(s"${os.toByteArray.length} bytes by Java-Serializable")
        val is = new ByteArrayInputStream(os.toByteArray)

        1 to LoopPerThread foreach { _ =>
          new ObjectInputStream(is).readObject()
          is.reset()
        }
      }
    }
  }

  "Kryo" should "尽可能快地序列化" in {
    elapsed("Serialize by Kryo") {
      duplicateInThreads(ThreadCount) {
        val message = LogEntry.newBuilder()
          .setName("Jeff").setResource("readme.txt").setIp("192.168.1.1")
          .build()

        val kryo = new Kryo()
        kryo.register(classOf[LogEntry])

        val os = new ByteArrayOutputStream()
        val output = new Output(os)

        1 to LoopPerThread foreach { _ =>
          kryo.writeObject(output, message)
          output.flush()
          output.clear()
          os.reset()
        }
      }
    }
  }

  it should "尽可能快地反序列化" in {
    elapsed("Deserialize by Kryo") {
      duplicateInThreads(ThreadCount) {
        val message = LogEntry.newBuilder()
          .setName("Jeff").setResource("readme.txt").setIp("192.168.1.1")
          .build()

        val kryo = new Kryo()
        kryo.register(classOf[LogEntry])

        val os = new ByteArrayOutputStream()
        val output = new Output(os)
        kryo.writeObject(output, message)
        output.flush()

        val bytes = os.toByteArray
//        println(s"${bytes.length} bytes by Kryo")

        1 to LoopPerThread foreach { _ =>
          kryo.readObject(new Input(bytes), classOf[LogEntry])
        }
      }
    }
  }

  private def elapsed(what: String)(action: => Unit): Unit = {
    val start = System.currentTimeMillis()
    action
    println(s"$what elapsed ${System.currentTimeMillis() - start}ms.")
  }

  private def duplicateInThreads(count: Int)(action: => Unit): Unit = {
    val executor = Executors.newFixedThreadPool(count)
    val latch = new CountDownLatch(count)

    1 to count foreach { _ =>
      executor.submit(new Runnable {
        override def run(): Unit = {
          action
          latch.countDown()
        }
      })
    }
    latch.await()
    executor.shutdown()
  }
}
