package com.afterqcd.study.kafka.clients.producer;

import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/3.
 */
public class JavaProducerBuilder {
    /**
     * Create producer builder.
     * @param keyClz
     * @param valueClz
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V>  ProducerBuilder<K, V> newBuilder(Class<K> keyClz, Class<V> valueClz) {
        return ProducerBuilder$.MODULE$.apply(new Properties(), keyClz, valueClz);
    }
}
