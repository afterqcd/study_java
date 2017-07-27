package com.afterqcd.study.spring.boot.aop;

import com.afterqcd.study.spring.boot.aop.auth.AuthRequired;
import com.afterqcd.study.spring.boot.aop.log.LoggingRequired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afterqcd on 16/6/1.
 *
 */
@Component
public class Library {
    private final List<String> books = new ArrayList<>();

    @LoggingRequired
    @AuthRequired
    public void addBook(String book) {
        books.add(book);
    }

    @LoggingRequired
    public List<String> getBooks() {
        return books;
    }
}
