package com.afterqcd.study.serde.protobuf;

import com.afterqcd.study.serde.protobuf.dto.Dto.Generic;
import com.afterqcd.study.serde.protobuf.opm.ConverterHolder;
import com.afterqcd.study.serde.protobuf.opm.DomainRegistry;
import com.google.protobuf.Any;
import com.google.protobuf.Message;
import net.badata.protobuf.converter.Converter;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoClasses;
import net.badata.protobuf.converter.type.TypeConverter;

/**
 * Created by afterqcd on 2016/12/22.
 */
public class GenericConverterImpl implements TypeConverter<Object, Generic> {
    private final DomainRegistry registry = DomainRegistry.getInstance();
    private final Converter converter = ConverterHolder.getConverter();

    @Override
    public Object toDomainValue(Object proto) {
        Generic generic = (Generic) proto;
        switch (generic.getValueCase()) {
        case BOOL:
            return generic.getBool();
        case INT:
            return generic.getInt();
        case LONG:
            return generic.getLong();
        case FLOAT:
            return generic.getFloat();
        case DOUBLE:
            return generic.getDouble();
        case STRING:
            return generic.getString();
        case ANY:
            return toDomain(generic.getAny());
        }
        return null;
    }

    private Object toDomain(Any any) {
        try {
            String name = getProtoName(any);
            Class<? extends Message> protoClass = registry.getProtoClassByName(name);
            Message message = any.unpack(protoClass);
            return converter.toDomain(registry.toDomainClass(protoClass), message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert " + any.getTypeUrl() + " to domain", e);
        }
    }

    private String getProtoName(Any any) {
        String url = any.getTypeUrl();
        int index = url.indexOf("/");
        return url.substring(index + 1);
    }

    @Override
    public Generic toProtobufValue(Object domain) {
        Generic.Builder builder = Generic.newBuilder();
        if (domain instanceof Boolean) {
            builder.setBool((Boolean) domain);
        } else if (domain instanceof Integer) {
            builder.setInt((Integer) domain);
        } else if (domain instanceof Long) {
            builder.setLong((Long) domain);
        } else if (domain instanceof Float) {
            builder.setFloat((Float) domain);
        } else if (domain instanceof Double) {
            builder.setDouble((Double) domain);
        } else if (domain instanceof String) {
            builder.setString((String) domain);
        } else if (domain.getClass().isAnnotationPresent(ProtoClass.class)) {
            ProtoClass protoClass = domain.getClass().getAnnotation(ProtoClass.class);
            Any any = toProtoAny(domain, protoClass);
            builder.setAny(any);
        } else if (domain.getClass().isAnnotationPresent(ProtoClasses.class)) {
            ProtoClass protoClass = domain.getClass().getAnnotation(ProtoClasses.class).value()[0];
            Any any = toProtoAny(domain, protoClass);
            builder.setAny(any);
        } else {
            throw new RuntimeException("Domain " + domain.getClass() + " has no ProtoClass/ProtoClasses annotation");
        }
        return builder.build();
    }

    private Any toProtoAny(Object domain, ProtoClass annotation) {
        Class<? extends Message> protoClass = annotation.value();
        return Any.pack(converter.toProtobuf(protoClass, domain));
    }
}
