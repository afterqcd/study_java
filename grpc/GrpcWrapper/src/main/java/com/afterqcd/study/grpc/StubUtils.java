package com.afterqcd.study.grpc;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.grpc.ServiceDescriptor;

/**
 * Created by afterqcd on 2017/2/10.
 */
public class StubUtils {
    private static final IStubListener stubListener = createStubListener();

    private static IStubListener createStubListener() {
        try {
            Config config = ConfigFactory.parseResources("grpc.properties");
            String stubListenerClassName = config.getString("stub.listener");
            return (IStubListener) Class.forName(stubListenerClassName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Stub创建时被调用。
     * @param targetService Stub调用的服务。
     */
    public static void onCreating(ServiceDescriptor targetService) {
        if (stubListener != null) {
            stubListener.onCreating(targetService);
        }
    }
}
