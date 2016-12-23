package com.afterqcd.study.serde.jprotobuf;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

/**
 * Created by afterqcd on 2016/12/21.
 */
public class User {
    @Protobuf(required = true, order = 1)
    private String name;
    @Protobuf(order = 2)
    private int age;

    public User() {

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
