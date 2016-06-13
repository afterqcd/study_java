package com.afterqcd.study.elasticsearch.update;

import com.afterqcd.study.elasticsearch.client.ClientUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.script.Script;

import java.net.UnknownHostException;

/**
 * Created by afterqcd on 16/6/13.
 */
public class EntryPoint {
    public static void main(String[] args) throws UnknownHostException {
        TransportClient client = ClientUtils.getClient("fengxing", "172.16.185.245", 9300);

        // 添加标签
        client.prepareUpdate("fengxing", "customer", "1")
                .setScript(new Script("ctx._source.tag += [\"教育\", \"运动\"]"))
                .execute()
                .actionGet();

        // 对标签去重
        client.prepareUpdate("fengxing", "customer", "1")
                .setScript(new Script("ctx._source.tag.unique()"))
                .execute()
                .actionGet();

        client.close();
    }
}
