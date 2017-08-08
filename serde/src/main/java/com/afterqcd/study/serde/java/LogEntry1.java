package com.afterqcd.study.serde.java;

import java.io.Serializable;

/**
 * Created by afterqcd on 2016/11/8.
 */
public class LogEntry1 implements Serializable {

    private String name;
    private String resource;
    private String ip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
