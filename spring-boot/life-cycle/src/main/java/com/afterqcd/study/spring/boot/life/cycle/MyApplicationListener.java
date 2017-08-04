package com.afterqcd.study.spring.boot.life.cycle;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("App Event\t\t# " + event.getClass().getSimpleName());
    }
}
