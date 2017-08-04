package com.afterqcd.study.serde.protobuf.opm;

import avro.shaded.com.google.common.collect.Maps;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import com.google.protobuf.Message;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.ProtocolMessageEnum;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoClasses;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

/**
 * Created by afterqcd on 2016/12/23.
 */
public class DomainRegistry {
    private final static DomainRegistry instance = new DomainRegistry();

    public static DomainRegistry getInstance() {
        return instance;
    }

    // registered records
    private final Set<Class<?>> registeredRecords = Sets.newConcurrentHashSet();

    // key is proto class, value is domain class
    private final Cache<Class<? extends Message>, Class<?>> protoDomainClassCache
            = CacheBuilder.newBuilder().build();
    // key is full name of proto class, value is proto class
    private final Cache<String, Class<? extends Message>> protoClassCache
            = CacheBuilder.newBuilder().build();

    // key is domain enum, value is proto enum
    private final Cache<
            Class<? extends Enum>,
            Map<? extends Enum, ? extends ProtocolMessageEnum>
            > domainProtoEnumCache = CacheBuilder.newBuilder().build();
    // key is proto enum, value is number to domain enum mapping
    private final Cache<
            Class<? extends ProtocolMessageEnum>,
            Map<? extends ProtocolMessageEnum, ? extends Enum>
            > protoDomainEnumCache = CacheBuilder.newBuilder().build();

    /**
     * Register domain class.
     * @param domainClass
     */
    public void registerClass(Class<?> domainClass) {
        if (registered(domainClass)) {
            return;
        }

        try {
            if (domainClass.isAnnotationPresent(ProtoClass.class)) {
                registerClassMapping(domainClass, domainClass.getAnnotation(ProtoClass.class).value());
            } else if (domainClass.isAnnotationPresent(ProtoClasses.class)) {
                ProtoClass[] protoClasses = domainClass.getAnnotation(ProtoClasses.class).value();
                for (ProtoClass protoClass : protoClasses) {
                    registerClassMapping(domainClass, protoClass.value());
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Can not register domain class " + domainClass.getName(), e);
        }
    }

    private boolean registered(Class<?> clz) {
        if (registeredRecords.contains(clz)) {
            return true;
        }
        synchronized (registeredRecords) {
            if (registeredRecords.contains(clz)) {
                return true;
            }
            registeredRecords.add(clz);
            return false;
        }
    }

    private void registerClassMapping(Class<?> domainClass, Class<? extends Message> protoClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        protoDomainClassCache.put(protoClass, domainClass);
        Descriptor descriptor =
                (Descriptor) protoClass.getDeclaredMethod("getDescriptor").invoke(null);
        protoClassCache.put(descriptor.getFullName(), protoClass);
    }

    /**
     * Get proto class
     * @param name
     * @return
     */
    public Class<? extends Message> getProtoClassByName(String name) {
        return protoClassCache.getIfPresent(name);
    }

    /**
     * Get domain class by proto class.
     * @param protoClass
     * @return
     */
    public Class<?> toDomainClass(Class<? extends Message> protoClass) {
        return protoDomainClassCache.getIfPresent(protoClass);
    }

    /**
     * Register domain enum.
     * @param domainEnum
     */
    public void registerEnum(Class<? extends Enum> domainEnum) {
        if (registered(domainEnum)) {
            return;
        }

        try {
            if (domainEnum.isAnnotationPresent(ProtoEnum.class)) {
                Class<? extends ProtocolMessageEnum> protoEnum
                        = domainEnum.getAnnotation(ProtoEnum.class).value();
                registerEnumMapping(domainEnum, protoEnum);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to register domain enum " + domainEnum.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private void registerEnumMapping(Class<? extends Enum> domainEnum,
                                     Class<? extends ProtocolMessageEnum> protoEnum)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<Enum, ProtocolMessageEnum> domainProtoEnumMapping
                = Maps.newHashMap();
        Map<ProtocolMessageEnum, Enum> protoDomainEnumMapping
                = Maps.newHashMap();
        Field[] declaredFields = domainEnum.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isEnumConstant() || field.isAnnotationPresent(ProtoEnumNumber.class)) {
                int number = field.getAnnotation(ProtoEnumNumber.class).value();
                ProtocolMessageEnum protoEnumValue = (ProtocolMessageEnum) protoEnum.getMethod("forNumber", int.class).invoke(null, number);
                Enum domainEnumValue = Enum.valueOf((Class<Enum>) field.getType(), field.getName());
                domainProtoEnumMapping.put(domainEnumValue, protoEnumValue);
                protoDomainEnumMapping.put(protoEnumValue, domainEnumValue);
            }
        }
        domainProtoEnumCache.put(domainEnum, domainProtoEnumMapping);
        protoDomainEnumCache.put(protoEnum, protoDomainEnumMapping);
    }

    /**
     * Clean all cached entries.
     */
    public void cleanUp() {
        registeredRecords.clear();
        protoClassCache.cleanUp();
        protoDomainClassCache.cleanUp();

        domainProtoEnumCache.cleanUp();
        protoDomainEnumCache.cleanUp();
    }

    /**
     * Get domain enum value
     * @param protoEnum
     */
    public Enum toDomainEnum(ProtocolMessageEnum protoEnum) {
        Map<? extends ProtocolMessageEnum, ? extends Enum> mapping
                = protoDomainEnumCache.getIfPresent(protoEnum.getClass());
        if (mapping == null) {
            throw new RuntimeException("Failed to convert value of proto enum " + protoEnum.getClass().getName() + " to domain enum value");
        }
        return mapping.get(protoEnum);
    }

    public ProtocolMessageEnum toProtoEnum(Enum domainEnum) {
        Map<? extends Enum, ? extends ProtocolMessageEnum> mapping
                = domainProtoEnumCache.getIfPresent(domainEnum.getClass());
        if (mapping == null) {
            throw new RuntimeException("Failed to convert value of domain enum " + domainEnum.getClass().getName() + " to proto enum value");
        }
        return mapping.get(domainEnum);
    }
}
