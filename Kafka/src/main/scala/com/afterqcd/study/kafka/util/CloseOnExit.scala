package com.afterqcd.study.kafka.util

/**
  * Created by afterqcd on 2016/11/30.
  */
trait CloseOnExit extends AutoCloseable { self =>
  @volatile private var closed = false

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run(): Unit = self.close()
  })

  override final def close(): Unit = {
    if (!closed) {
      self.synchronized {
        if (!closed) {
          doClose()
          closed = true
        }
      }
    }
  }

  protected def doClose(): Unit
}
