package com.afterqcd.study.spring.di.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by afterqcd on 16/6/7.
 */
public class EntryPoint {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
        ((TextEditor) context.getBean("textEditor")).spellCheck();

        System.out.println(context.getBean("textEditor"));
        System.out.println(context.getBean("anotherTextEditor"));
    }
}
