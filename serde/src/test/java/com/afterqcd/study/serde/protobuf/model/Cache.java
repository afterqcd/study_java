package com.afterqcd.study.serde.protobuf.model;

import java.util.Map;

/**
 * Created by afterqcd on 2016/12/21.
 */
public class Cache {
    private String name;
    private Map<String, String> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
