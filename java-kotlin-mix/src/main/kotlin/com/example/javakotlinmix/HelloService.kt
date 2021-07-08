package com.example.javakotlinmix

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * @author qiuchangdong
 * @Type Service
 * @Desc 。
 * @date 2021-07-08 10:01:07
 */
@Service
class HelloService(
    val helloRepository: HelloRepository,
    @Value("\${hello.message}") val helloMessage: String) {
    fun hello(): String {
        return "${helloRepository.hello()} -> $helloMessage"
    }

    fun data(): List<String> {
        val data = helloRepository.data()
        return data.map { "$it processed by kotlin" }
    }
}