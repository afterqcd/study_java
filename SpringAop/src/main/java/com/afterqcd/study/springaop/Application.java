package com.afterqcd.study.springaop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by afterqcd on 16/6/1.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private Library library;

    public void run(String... args) throws Exception {
        library.addBook("Spring In Action");
        library.addBook("Spring Security");
        System.out.println(library.getBooks());
    }
}
