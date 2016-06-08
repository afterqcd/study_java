package com.afterqcd.study.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/8.
 */
@Component
public class ContextClosedHandler implements ApplicationListener<ContextClosedEvent> {
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        System.out.println("Context closed");
    }
}
