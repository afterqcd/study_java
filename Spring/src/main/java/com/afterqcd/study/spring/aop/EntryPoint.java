package com.afterqcd.study.spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by afterqcd on 16/6/10.
 */
public class EntryPoint {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        context.getBean(HelloWorld.class).sayHello();

        StudentService studentService = context.getBean(StudentService.class);
        studentService.add("bob");
        studentService.add("john");
        studentService.remove("bob");
        studentService.update("john");
    }
}
