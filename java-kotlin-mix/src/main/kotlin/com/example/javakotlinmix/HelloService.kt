package com.example.javakotlinmix

import org.springframework.stereotype.Service

/**
 * @author qiuchangdong
 * @Type Service
 * @Desc 。
 * @date 2021-07-08 10:01:07
 */
@Service
class Service(val repository: Repository) {
    fun hello(): String {
        return "Kotlin Service -> " + repository.hello()
    }
}