package com.afterqcd.study.springaop.auth;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/1.
 */
@Aspect
@Order(0)
@Component
public class AuthAspect {
    @Value("${auth.name}")
    private String name;

    @Value("${auth.password}")
    private String password;

    @Before("@annotation(AuthRequired)")
    public void authorize(JoinPoint joinPoint) {
        if (!name.equals("test") || !password.equals("test")) {
            throw new RuntimeException("Failed to authorizing");
        }
        System.out.println("Succeed to authorize to " + joinPoint.toShortString());
    }
}
