package com.afterqcd.study.elasticsearch.index;

import com.afterqcd.study.elasticsearch.client.ClientUtils;
import com.google.common.collect.Maps;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by afterqcd on 16/6/13.
 */
public class EntryPoint {
    public static void main(String[] args) throws UnknownHostException {
        Map<String, Object> bob = Maps.newHashMap();
        bob.put("name", "bob");
        bob.put("tag", new String[] {"中", "新闻"});

        TransportClient client = ClientUtils.getClient("fengxing", "172.16.185.245", 9300);
        ListenableActionFuture<IndexResponse> future
                = client.prepareIndex("fengxing", "customer", "1")
                .setSource(bob)
                .execute();
        IndexResponse response = future.actionGet();
        System.out.println(response);
        client.close();
    }
}
