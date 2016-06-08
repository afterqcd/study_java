package com.afterqcd.study.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/8.
 */
@Component
public class ContextStartedHandler implements ApplicationListener<ContextStartedEvent> {
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        System.out.println("Context started");
    }
}
