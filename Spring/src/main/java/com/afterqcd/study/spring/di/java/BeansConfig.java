package com.afterqcd.study.spring.di.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by afterqcd on 16/6/7.
 */
@Configuration
@ComponentScan(basePackages = "com.afterqcd.study.spring.di.java")
public class BeansConfig {
    @Bean
    TextEditor anotherTextEditor() {
        return new TextEditor();
    }
}
