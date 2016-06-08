package com.afterqcd.study.spring.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/8.
 */
@Component
public class HelloWorld {
    @Value("${hello.message}")
    private String message;

    public void sayHello() {
        System.out.println("Your message: " + this.message);
    }
}
