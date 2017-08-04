package com.afterqcd.study.grpc;

import io.grpc.ServiceDescriptor;

/**
 * Created by afterqcd on 2017/2/10.
 */
public interface IStubListener {
    void onCreating(ServiceDescriptor targetService);
}
