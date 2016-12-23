package com.afterqcd.study.serde.protobuf.opm;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Created by afterqcd on 2016/12/23.
 */
public class DomainRegistry {
    private final static DomainRegistry instance = new DomainRegistry();

    public static DomainRegistry getInstance() {
        return instance;
    }

    private Cache<Class<?>, Class<?>> protoDomainCache = CacheBuilder.newBuilder().build();
}
