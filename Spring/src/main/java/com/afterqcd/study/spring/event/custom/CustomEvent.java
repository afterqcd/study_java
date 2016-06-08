package com.afterqcd.study.spring.event.custom;

import org.springframework.context.ApplicationEvent;

/**
 * Created by afterqcd on 16/6/8.
 */
public class CustomEvent extends ApplicationEvent {
    public CustomEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "CustomEvent from " + source;
    }
}
