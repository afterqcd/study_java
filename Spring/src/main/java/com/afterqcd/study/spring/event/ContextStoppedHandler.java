package com.afterqcd.study.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/8.
 */
@Component
public class ContextStoppedHandler implements ApplicationListener<ContextStoppedEvent> {
    public void onApplicationEvent(ContextStoppedEvent contextStoppedEvent) {
        System.out.println("Context stopped");
    }
}
