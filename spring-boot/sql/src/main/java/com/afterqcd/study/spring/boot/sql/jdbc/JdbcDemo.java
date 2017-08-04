package com.afterqcd.study.spring.boot.sql.jdbc;

import com.afterqcd.study.spring.boot.sql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class JdbcDemo implements ApplicationRunner {
    @Autowired
    private UserDao userDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Stream.of(1L, 2L).forEach(userId -> {
            System.out.println("Found by jdbc " + userDao.findUser(userId));
        });

        userDao.createUser(new User(3L, "jdbc demo", 50));
        System.out.println("Created by jdbc " + userDao.findUser(3L));
    }

}
