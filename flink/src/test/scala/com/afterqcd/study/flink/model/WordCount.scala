package com.afterqcd.study.flink.model

/**
  * Created by afterqcd on 2017/4/1.
  */
case class WordCount(word: String, count: Int) {
  def +(another: WordCount): WordCount = {
    WordCount(word, count + another.count)
  }
}
