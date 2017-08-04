package com.afterqcd.study.spring.boot.sql.jpa;

import com.afterqcd.study.spring.boot.sql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class JpaDemo implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Stream.of(1L, 2L).forEach(userId ->
                System.out.println("Found by jpa " + userRepository.findOne(userId))
        );

        userRepository.save(new User(4L, "jpa demo", 50));
        System.out.println("Found by jpa " + userRepository.findOne(4L));

        Stream.of(1L, 2L, 4L).forEach(userId -> {
            System.out.println("Found by jpa with customized method " + userRepository.findById(userId));
            System.out.println("Found by jpa with @Query " + userRepository.queryById(userId));
        });
    }
}
