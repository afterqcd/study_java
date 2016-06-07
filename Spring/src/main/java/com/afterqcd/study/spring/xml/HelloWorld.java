package com.afterqcd.study.spring.xml;

/**
 * Created by afterqcd on 16/6/7.
 */
public class HelloWorld {
    private String message;

    public HelloWorld() {

    }

    public HelloWorld(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void init() {
        System.out.println("Initialized hello world with message '" + message + "' in init-method");
    }

    public void destroy() {
        System.out.println("Destroyed hello world in destroy-method");
    }

    public void sayHello() {
        System.out.println("You message: " + message);
    }
}
