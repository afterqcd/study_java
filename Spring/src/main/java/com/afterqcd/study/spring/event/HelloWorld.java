package com.afterqcd.study.spring.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/8.
 */
@Component
public class HelloWorld {
    @Value("Hello Spring")
    private String message;

    public void sayHello() {
        System.out.println("Your message '" + message + "'.");
    }
}
