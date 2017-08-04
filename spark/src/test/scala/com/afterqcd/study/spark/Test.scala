package com.afterqcd.study.spark

import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

/**
  * Created by afterqcd on 2016/10/11.
  */
class Test extends FlatSpec with Matchers with BeforeAndAfterAll {
  override protected def beforeAll(): Unit = {
    println("before all")
  }

  override protected def afterAll(): Unit = {
    println("after all")
  }

  "Test" should "be:支持值判等" in {
    1 should be (1)
    "a" should be ("a")
  }

  it should "contain:支持集合元素判等" in {
    Array(1, 2, 3) should contain theSameElementsAs Array(3, 2, 1)
    Array(1, 2, 3) should contain theSameElementsInOrderAs Array(1, 2, 3)
    Array(1) should contain only 1
    Array(2, 4) should contain oneOf (1, 2, 3)
  }
}
