package com.afterqcd.study.grpc;

import io.grpc.ServiceDescriptor;

/**
 * Created by afterqcd on 2017/2/10.
 */
public class DefaultStubListener implements IStubListener {
    @Override
    public void onCreating(ServiceDescriptor targetService) {
        System.out.println("Creating stub of " + targetService.getName());
    }
}
