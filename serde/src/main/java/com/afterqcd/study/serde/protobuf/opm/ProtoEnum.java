package com.afterqcd.study.serde.protobuf.opm;

import com.google.protobuf.ProtocolMessageEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by afterqcd on 2016/12/23.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ProtoEnum {
    /**
     * Retrieve related protobuf enum.
     * @return enum that represents protobuf dto.
     */
    Class<? extends ProtocolMessageEnum> value();
}
