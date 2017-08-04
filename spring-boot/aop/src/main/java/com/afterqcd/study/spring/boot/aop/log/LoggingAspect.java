package com.afterqcd.study.spring.boot.aop.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by afterqcd on 16/6/1.
 */
@Aspect
@Order(100)
@Component
public class LoggingAspect {
    @Before(value = "@annotation(LoggingRequired)")
    public void logging(JoinPoint joinPoint) {
        System.out.println(joinPoint.toShortString() + " with argument(s) " + Arrays.toString(joinPoint.getArgs()));
    }
}
