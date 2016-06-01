package com.afterqcd.study.springrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by afterqcd on 16/6/1.
 */
@SpringBootApplication
@EnableWebMvc
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
