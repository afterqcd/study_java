package com.afterqcd.study.serde.protobuf.model;

import com.afterqcd.study.serde.protobuf.dto.Dto;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Created by afterqcd on 2016/12/21.
 */
@ProtoClass(Dto.Context.class)
public class Context {
    @ProtoField
    private NamedData<String> desc;
    @ProtoField
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
