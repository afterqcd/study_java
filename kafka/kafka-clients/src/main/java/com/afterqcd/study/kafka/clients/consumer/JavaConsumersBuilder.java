package com.afterqcd.study.kafka.clients.consumer;

import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/3.
 */
public class JavaConsumersBuilder {
    /**
     * Create consumers builder.
     * @param keyClz
     * @param valueClz
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V>  ConsumersBuilder<K, V> newBuilder(Class<K> keyClz, Class<V> valueClz) {
        return ConsumersBuilder$.MODULE$.apply(new Properties(), keyClz, valueClz);
    }
}
