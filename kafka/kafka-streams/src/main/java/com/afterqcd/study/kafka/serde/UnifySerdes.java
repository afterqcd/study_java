package com.afterqcd.study.kafka.serde;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class UnifySerdes {
    /**
     * Get Serde for specified class.
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> Serde<T> serde(Class<T> clz) {
        try {
            return Serdes.serdeFrom(clz);
        } catch (IllegalArgumentException e) {
            try {
                return ProtoBufSerde.serde(clz);
            } catch (IllegalArgumentException e1) {
                throw new IllegalArgumentException(clz.getName() + "is not a kafka support class, nor derives from protobuf MessageLite", e1);
            }
        }
    }
}
