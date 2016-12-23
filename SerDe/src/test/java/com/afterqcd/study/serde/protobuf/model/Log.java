package com.afterqcd.study.serde.protobuf.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Created by afterqcd on 2016/12/21.
 */
public class Log {
    private Level level;
    private String message;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
