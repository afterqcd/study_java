package com.afterqcd.study.spring.boot.life.cycle.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Child {
    @PostConstruct
    void postConstruct() {
        System.out.println("Bean child\t\t# PostConstruct");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("Bean child\t\t# PreDestroy");
    }
}
