package com.afterqcd.study.serde.protobuf.opm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by afterqcd on 2016/12/23.
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ProtoEnumNumber {
    /**
     * Retrieve number for field in protobuf enum.
     * @return number for field in protobuf enum.
     */
    int value();
}
