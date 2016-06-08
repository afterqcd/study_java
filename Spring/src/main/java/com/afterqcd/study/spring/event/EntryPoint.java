package com.afterqcd.study.spring.event;

import com.afterqcd.study.spring.event.custom.CustomEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by afterqcd on 16/6/8.
 */
public class EntryPoint {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext("com.afterqcd.study.spring.event");
        context.registerShutdownHook(); // 程序退出前调用context.doClose,进而发布closed事件
        // 发布started事件
        context.start();
        context.getBean(HelloWorld.class).sayHello();
        // 发布stopped事件
        context.stop();

        // 发布自定义事件
        context.getBean(CustomEventPublisher.class).publish();
    }
}
