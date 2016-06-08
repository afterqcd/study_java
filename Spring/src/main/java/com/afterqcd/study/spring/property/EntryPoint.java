package com.afterqcd.study.spring.property;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by afterqcd on 16/6/8.
 */
public class EntryPoint {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        context.getBean(HelloWorld.class).sayHello();
    }
}
