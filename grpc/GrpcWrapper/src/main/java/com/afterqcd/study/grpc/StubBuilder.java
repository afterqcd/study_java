package com.afterqcd.study.grpc;

import com.google.common.base.Strings;
import io.grpc.Channel;

import java.lang.reflect.*;
import java.lang.reflect.Method;

/**
 * Created by afterqcd on 2017/2/9.
 */
public class StubBuilder {
    private final Class<?> grpcClass;
    private java.lang.reflect.Method newStubMethod;
    private java.lang.reflect.Method newBlockingStubMethod;
    private java.lang.reflect.Method newFutureStubMethod;

    private Channel channel;
    private String targetServiceName;

    private StubBuilder(Class<?> grpcClass){
        this.grpcClass = grpcClass;
        initStubMethods();
    }

    private void initStubMethods() {
        try {
            this.newStubMethod = grpcClass.getDeclaredMethod("newStub", Channel.class);
            this.newBlockingStubMethod = grpcClass.getDeclaredMethod("newBlockingStub", Channel.class);
            this.newFutureStubMethod = grpcClass.getDeclaredMethod("newFutureStub", Channel.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(grpcClass.getName() + " is not a generated gRPC class", e);
        }
    }

    /**
     * 创建指定gRPC类的Stub构建器。
     * @param grpcClass
     * @return
     */
    public static StubBuilder newBuilder(Class<?> grpcClass) {
        return new StubBuilder(grpcClass);
    }

    /**
     * 提供Channel。
     * @param channel
     * @return
     */
    public StubBuilder channel(Channel channel) {
        this.channel = channel;
        return this;
    }

    /**
     * 提供Stub要访问的服务的名称
     * @param targetServiceName
     * @return
     */
    public StubBuilder targetServiceName(String targetServiceName) {
        this.targetServiceName = targetServiceName;
        return this;
    }

    /**
     * 创建默认Stub（异步的）。
     * @param <S>
     * @return
     */
    public <S> S buildStub() {
        return stub(newStubMethod);
    }

    /**
     * 创建阻塞Stub（同步的）。
     * @param <S>
     * @return
     */
    public <S> S buildBlockingStub() {
        return stub(newBlockingStubMethod);
    }

    /**
     * 创建基于Future的Stub（异步的）。
     * @param <S>
     * @return
     */
    public <S> S buildFutureStub() {
        return stub(newFutureStubMethod);
    }

    @SuppressWarnings("unchecked")
    private <S> S stub(Method method) {
        if (Strings.isNullOrEmpty(targetServiceName)) {
            throw new IllegalArgumentException("The name of target service is null or empty");
        }
        if (channel == null) {
            throw new IllegalArgumentException("Channel is null");
        }

        try {
            return (S) method.invoke(null, channel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(grpcClass.getName() + " is not a generated gRPC class", e);
        }
    }
}
