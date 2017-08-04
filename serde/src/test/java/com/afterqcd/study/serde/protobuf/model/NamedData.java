package com.afterqcd.study.serde.protobuf.model;

import com.afterqcd.study.serde.protobuf.GenericConverterImpl;
import com.afterqcd.study.serde.protobuf.dto.Dto;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Created by afterqcd on 2016/12/21.
 */
@ProtoClass(Dto.NamedData.class)
public class NamedData<T> {
    @ProtoField
    private String name;
    @ProtoField(converter = GenericConverterImpl.class)
    private T data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
