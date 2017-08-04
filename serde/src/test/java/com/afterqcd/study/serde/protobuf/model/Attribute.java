package com.afterqcd.study.serde.protobuf.model;

import com.afterqcd.study.serde.protobuf.GenericConverterImpl;
import com.afterqcd.study.serde.protobuf.dto.Dto;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Created by afterqcd on 2016/12/21.
 */
@ProtoClass(Dto.Attribute.class)
public class Attribute {
    @ProtoField
    private String name;
    @ProtoField(converter = GenericConverterImpl.class)
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
