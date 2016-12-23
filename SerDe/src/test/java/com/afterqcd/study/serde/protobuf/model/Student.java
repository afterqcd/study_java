package com.afterqcd.study.serde.protobuf.model;

import java.util.List;

/**
 * Created by afterqcd on 2016/12/21.
 */
public class Student {
    private String name;
    private List<String> interests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
