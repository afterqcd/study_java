package com.afterqcd.study.spring.boot.life.cycle.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Parent {
    @Autowired
    Child child;

    @PostConstruct
    void postConstruct() {
        System.out.println("Bean parent\t\t# PostConstruct");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("Bean parent\t\t# PreDestroy");
    }
}
