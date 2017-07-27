package com.afterqcd.study.kafka.serde;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class ProtoBufSerde<T> implements Serde<T> {
    private final Class<T> clz;
    private final Serializer<T> serializer;
    private final Deserializer<T> deserializer;

    private ProtoBufSerde(Class<T> clz) {
        this.clz = clz;
        this.serializer = new ProtobufSerializer();
        this.deserializer = new ProtobufDeserializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    @Override
    public void close() {
        // nothing to do
    }

    @Override
    public Serializer<T> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<T> deserializer() {
        return deserializer;
    }

    private class ProtobufSerializer implements Serializer<T> {

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            // nothing to do
        }

        @Override
        public byte[] serialize(String topic, T data) {
            if (data instanceof MessageLite) {
                return ((MessageLite)data).toByteArray();
            }
            throw new IllegalArgumentException(data.getClass().getName() + " is not derived from protobuf MessageLite");
        }

        @Override
        public void close() {
            // nothing to do
        }
    }

    @SuppressWarnings("unchecked")
    private class ProtobufDeserializer implements Deserializer<T> {
        private Parser<T> parser;

        {
            try {
                Constructor<T> constructor = clz.getDeclaredConstructor();
                constructor.setAccessible(true);
                this.parser = (Parser<T>) ((MessageLite)constructor.newInstance()).getParserForType();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            // nothing to do
        }

        @Override
        public T deserialize(String topic, byte[] data) {
            try {
                return parser.parseFrom(data);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException("It's not a valid protobuf bytes", e);
            }
        }

        @Override
        public void close() {
            // nothing to do
        }
    }

    /**
     * Create new Serde.
     * @param clz
     * @return
     */
    static <T> ProtoBufSerde<T> serde(Class<T> clz) {
        if (!MessageLite.class.isAssignableFrom(clz)) {
            throw new IllegalArgumentException(clz.getName() + " is not derived from protobuf MessageLite");
        }
        return new ProtoBufSerde<>(clz);
    }
}
