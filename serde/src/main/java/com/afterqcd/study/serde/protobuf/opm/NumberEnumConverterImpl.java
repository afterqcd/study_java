package com.afterqcd.study.serde.protobuf.opm;

import com.google.protobuf.ProtocolMessageEnum;
import net.badata.protobuf.converter.type.TypeConverter;

/**
 * Created by afterqcd on 2016/12/23.
 */
public class NumberEnumConverterImpl implements TypeConverter<Object, Object> {
    private final DomainRegistry registry = DomainRegistry.getInstance();

    @Override
    public Object toDomainValue(Object obj) {
        return registry.toDomainEnum((ProtocolMessageEnum) obj);
    }

    @Override
    public Object toProtobufValue(Object obj) {
        return registry.toProtoEnum((Enum) obj);
    }
}
