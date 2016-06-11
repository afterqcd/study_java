package com.afterqcd.study.spring.aop.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by afterqcd on 16/6/10.
 */
@Component
@Aspect
public class LoggingAspect {
    @Pointcut(value = "@target(com.afterqcd.study.spring.aop.log.RequireLogging) || @annotation(com.afterqcd.study.spring.aop.log.RequireLogging)")
    public void requireLogging() {
    }

    @Before("requireLogging()")
    public void before(JoinPoint joinPoint) {
        String signature = joinPoint.getSignature().toShortString();
        String arguments = Arrays.toString(joinPoint.getArgs());
        System.out.println("Invoking " + signature + " with " + arguments);
    }

    @AfterReturning(value = "requireLogging() && execution(!void *.*(..))", returning = "returnValue")
    public void afterReturning(Object returnValue) {
        System.out.println("Returning " + returnValue);
    }

    @AfterThrowing(value = "requireLogging()", throwing = "ex")
    public void afterThrowing(Exception ex) {
        System.out.println(ex.getMessage());
        ex.printStackTrace();
    }
}
