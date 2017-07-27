package com.afterqcd.study.spring.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    @RequestMapping("/hello")
    String hello() {
        return "Hello spring boot!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
