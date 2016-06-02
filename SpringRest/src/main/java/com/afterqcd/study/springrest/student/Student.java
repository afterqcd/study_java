package com.afterqcd.study.springrest.student;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Created by afterqcd on 16/6/1.
 */
public class Student {
    private long id;
    @NotEmpty
    private String name;
    @Range(min = 0, max = 1024)
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
}
