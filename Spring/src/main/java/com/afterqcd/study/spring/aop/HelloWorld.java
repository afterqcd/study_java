package com.afterqcd.study.spring.aop;

import com.afterqcd.study.spring.aop.log.RequireLogging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/10.
 */
@Component
public class HelloWorld {
    @Value("Hello World!")
    private String message;

    @RequireLogging
    public void sayHello() {
        System.out.println("Your message: '" + message + "'.");
    }
}
