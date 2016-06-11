package com.afterqcd.study.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by afterqcd on 16/6/10.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.afterqcd.study.spring.aop")
public class ApplicationConfig {
}
