package com.afterqcd.study.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/8.
 */
@Component
public class ContextRefreshedHandler implements ApplicationListener<ContextRefreshedEvent> {
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("Context refreshed");
    }
}
