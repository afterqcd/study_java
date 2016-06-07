package com.afterqcd.study.spring.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by afterqcd on 16/6/7.
 */
public class EntryPoint {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/beans.xml");
        /*
           显示调用context.destroy或者注册ShutdownHook,才会触发bean.destroy-method;
           ShutdownHook内部只是简单调用了context.close
         */
        context.registerShutdownHook();

        hello(context);
        constructor(context);
        scope(context);
        innerBean(context);
        injectionCollection(context);
        nullOrEmpty(context);
    }

    private static void scope(ClassPathXmlApplicationContext context) {
        System.out.println(context.getBean("helloWorld").equals(context.getBean("helloWorld")));
        System.out.println(!context.getBean("prototypeHelloWorld").equals(context.getBean("prototypeHelloWorld")));
    }

    private static void constructor(ClassPathXmlApplicationContext context) {
        ((HelloWorld) context.getBean("constructorHelloWorld")).sayHello();
        ((TextEditor) context.getBean("constructorTextEditor")).spellCheck();
    }

    private static void hello(ClassPathXmlApplicationContext context) {
        HelloWorld helloWorld = (HelloWorld) context.getBean("helloWorld");
        helloWorld.sayHello();
    }

    private static void innerBean(ClassPathXmlApplicationContext context) {
        TextEditor textEditor = (TextEditor) context.getBean("textEditor");
        textEditor.spellCheck();
    }

    private static void injectionCollection(ClassPathXmlApplicationContext context) {
        JavaCollections collections = (JavaCollections) context.getBean("javaCollections");
        System.out.println(collections.getList());
        System.out.println(collections.getSet());
        System.out.println(collections.getMap());
        System.out.println(collections.getProperties());
    }

    private static void nullOrEmpty(ClassPathXmlApplicationContext context) {
        ((HelloWorld) context.getBean("emptyHelloWorld")).sayHello();
        ((HelloWorld) context.getBean("nullHelloWorld")).sayHello();
    }
}
