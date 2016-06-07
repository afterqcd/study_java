package com.afterqcd.study.spring.annotation;

import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/7.
 */
@Component
public class SpellChecker {
    public void check() {
        System.out.println("SpellChecker.check");
    }
}
