package com.afterqcd.study.spring.boot.web.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class UserSupplier {

    @Bean
    ConcurrentHashMap<Long, User> allUsers() {
        ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
        users.put(1L, new User(1L, "zhang san", 3));
        users.put(2L, new User(2L, "wang wu", 5));
        return users;
    }
}
