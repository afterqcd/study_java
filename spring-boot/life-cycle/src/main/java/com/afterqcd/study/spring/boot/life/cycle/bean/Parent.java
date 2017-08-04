package com.afterqcd.study.spring.boot.life.cycle.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Parent {
    @Autowired
    Child child;

    /**
     * 使用注解观察Bean的生命周期对代码侵入更少
     */
    @PostConstruct
    void postConstruct() {
        System.out.println("Bean parent\t\t# PostConstruct");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("Bean parent\t\t# PreDestroy");
    }
}
