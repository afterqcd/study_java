package com.afterqcd.study.spring.boot.sql;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.MessageFormat;

@Entity(name = "users")
public class User {
    @Id
    private long id;
    private String name;
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User() {
    }

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return MessageFormat.format("User(id={0}, name={1}, age={2})", id, name, age);
    }
}
