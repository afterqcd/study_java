package com.afterqcd.study.serde.protobuf;

import com.afterqcd.study.serde.protobuf.dto.Dto.Generic;
import net.badata.protobuf.converter.type.TypeConverter;

/**
 * Created by afterqcd on 2016/12/22.
 */
public class ObjectGenericConverterImpl implements TypeConverter<Object, Generic> {
    @Override
    public Object toDomainValue(Object o) {
        Generic generic = (Generic) o;
        switch (generic.getValueCase()) {
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
            return generic.getAny().getDescriptorForType();
        }
        return null;
    }

    @Override
    public Generic toProtobufValue(Object o) {
        return null;
    }
}
