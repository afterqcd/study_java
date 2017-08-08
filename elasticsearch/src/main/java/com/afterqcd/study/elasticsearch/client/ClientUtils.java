package com.afterqcd.study.elasticsearch.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by afterqcd on 16/6/13.
 */
public class ClientUtils {
    public static TransportClient getClient(String cluster, String ip, int port)
            throws UnknownHostException {
        InetSocketTransportAddress address = new InetSocketTransportAddress(
                InetAddress.getByName(ip), port
        );
        return getClient(cluster, address);
    }

    public static TransportClient getClient(String cluster, InetSocketTransportAddress... addresses) {
        Settings settings = Settings.builder().put("cluster.name", cluster).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddresses(addresses);
        return client;
    }
}
