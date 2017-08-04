package com.afterqcd.study.java.jdbc;

/**
 * Created by afterqcd on 16/6/11.
 */
public class Student {
    private Long id;
    private int age;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", age: " + age + ", name: " + name + " }";
    }
}
