package com.afterqcd.study.serde.protobuf.model;

/**
 * Created by afterqcd on 2016/12/21.
 */
public enum Level {
    Debug("debug"),
    Info("info"),
    Warn("warn"),
    Error("error");

    private final String desc;
    Level(String desc) {
        this.desc = desc;
    }
}
