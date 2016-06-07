package com.afterqcd.study.spring.annotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by afterqcd on 16/6/7.
 */
public class EntryPoint {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("annotation/beans.xml");
        context.getBean(TextEditor.class).spellCheck();
    }
}
