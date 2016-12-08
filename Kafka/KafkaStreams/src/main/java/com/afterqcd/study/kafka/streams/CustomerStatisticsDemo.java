package com.afterqcd.study.kafka.streams;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class CustomerStatisticsDemo {
    private static final String AppPropertiesResources = "application.properties";

    public static void main(String[] args) {

    }

    private static Properties streamsProps() {
        Config config = ConfigFactory.parseResources(AppPropertiesResources);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-lambda-example");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.getString("kafka.bootstrap.servers"));
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, config.getString("zookeeper.connect"));
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
        return props;
    }
}
