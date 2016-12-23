package com.afterqcd.study.serde.protobuf.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Created by afterqcd on 2016/12/21.
 */
public class Context {
    private NamedData<String> desc;
    private NamedData<Double> size;

    public NamedData<String> getDesc() {
        return desc;
    }

    public void setDesc(NamedData<String> desc) {
        this.desc = desc;
    }

    public NamedData<Double> getSize() {
        return size;
    }

    public void setSize(NamedData<Double> size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
