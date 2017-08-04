package com.afterqcd.study.elasticsearch.get;

import com.afterqcd.study.elasticsearch.client.ClientUtils;
import com.google.gson.Gson;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by afterqcd on 16/6/13.
 */
public class EntryPoint {
    public static void main(String[] args) throws UnknownHostException {
        TransportClient client = ClientUtils.getClient("fengxing", "172.16.185.245", 9300);
        GetResponse response = client.prepareGet("fengxing", "customer", "1").execute().actionGet();

        Map<String, Object> map = response.getSourceAsMap();
        map.put("id", response.getId());
        System.out.println(new Gson().toJson(map));

        client.close();
    }
}
