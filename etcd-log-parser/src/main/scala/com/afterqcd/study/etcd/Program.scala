package com.afterqcd.study.etcd

import java.io.{File, PrintWriter}

import com.google.common.base.Strings
import com.google.common.io.Files
import com.google.gson.{GsonBuilder, JsonParser}

/**
  * Created by afterqcd on 2017/3/8.
  */
object Program {
  private val gson = new GsonBuilder().setPrettyPrinting().create()

  def main(args: Array[String]): Unit = {
    val resource = "docker_stop_pause_container"
    new Parser(resourceAsFile(resource)).entries.zipWithIndex.foreach { case (entry, index) =>
      val alignedIndex = f"$index%03d"
      writeFile(
        s"/tmp/kubernetes/$resource/$alignedIndex.${asFileName(entry.name)}",
        toPrettyJson(entry.value)
      )
    }
  }

  private def resourceAsFile(resource: String): String = {
    Program.getClass.getClassLoader.getResource(resource).getFile
  }

  private def asFileName(name: String): String = {
    name.replaceAll("[\\[\\]]", "").replaceAll(" /", "--").replaceAll("/", "_")
  }

  private def writeFile(name: String, content: String): Unit = {
    val file = new File(name)
    Files.createParentDirs(file)
    val pw = new PrintWriter(file)
    pw.write(content)
    pw.close()
  }

  private def toPrettyJson(json: String): String = {
    if (Strings.isNullOrEmpty(json)) {
      ""
    } else {
      val jsonObject = new JsonParser().parse(json).getAsJsonObject
      gson.toJson(jsonObject)
    }
  }
}
