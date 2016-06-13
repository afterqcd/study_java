package com.afterqcd.study.elasticsearch.query;

import com.afterqcd.study.elasticsearch.client.ClientUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;

import java.net.UnknownHostException;

/**
 * Created by afterqcd on 16/6/13.
 */
public class EntryPoint {
    public static void main(String[] args) throws UnknownHostException {
        TransportClient client = ClientUtils.getClient("fengxing", "172.16.185.245", 9300);


        System.out.println("查询: 'tag:(中 AND 新闻)'");
        System.out.println(
                client.prepareSearch("fengxing")
                        .setTypes("customer")
                        .setQuery(QueryBuilders.queryStringQuery("tag:(\"中\" AND \"新闻\")"))
                        .execute()
                        .actionGet()
        );

        System.out.println("查询: 'tag:(中 AND 新闻 NOT 运动)'");
        System.out.println(
                client.prepareSearch("fengxing")
                .setTypes("customer")
                .setQuery(QueryBuilders.queryStringQuery("tag:(\"中\" AND \"新闻\" NOT \"运动\")"))
                .execute()
                .actionGet()
        );


        client.close();
    }
}
