package com.afterqcd.study.spring.boot.life.cycle;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener {
    @EventListener
    void onEvent(Object event) {
        System.out.println("Context Event\t# " + event.getClass().getSimpleName());
    }
}
