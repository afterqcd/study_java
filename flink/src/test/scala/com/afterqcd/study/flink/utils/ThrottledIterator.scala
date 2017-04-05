package com.afterqcd.study.flink.utils

/**
  * Created by afterqcd on 2017/4/1.
  */
class ThrottledIterator[T](val source: Seq[T], val elementsPerSecond: Int)
  extends Iterator[T] with Serializable  {

  assert(source != null, "source must be not null")
  assert(source.isInstanceOf[Serializable], "source must be serializable")
  assert(elementsPerSecond > 0, "'elements per second' must be positive and not zero")

  @transient private lazy val sourceIterator = source.iterator
  private val batchTimeInMillis = 50.max((1000F / elementsPerSecond).toInt)
  private val batchSize = elementsPerSecond * batchTimeInMillis / 1000
  private var lastCheckTime = 0L
  private var num = 0

  override def hasNext: Boolean = sourceIterator.hasNext

  override def next(): T = {
    if (lastCheckTime == 0L) {
      lastCheckTime = System.currentTimeMillis()
    }

    // 放完一个批次后做重置和等待处理
    if (num == batchSize) {
      num = 0

      val now = System.currentTimeMillis()
      val elapsed = now - lastCheckTime
      if (elapsed < batchTimeInMillis) {
        Thread.sleep(batchTimeInMillis - elapsed)
      }

      lastCheckTime += batchTimeInMillis
    }

    num += 1
    sourceIterator.next()
  }
}

object ThrottledIterator {
  def apply[T](source: Seq[T], elementsPerSecond: Int): ThrottledIterator[T] = {
    new ThrottledIterator(source, elementsPerSecond)
  }
}
