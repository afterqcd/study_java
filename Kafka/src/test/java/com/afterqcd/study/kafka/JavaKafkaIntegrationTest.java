package com.afterqcd.study.kafka;

import com.afterqcd.study.kafka.unit.KafkaUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Created by afterqcd on 2016/12/3.
 */
public class JavaKafkaIntegrationTest {
    protected static KafkaUnit kafkaUnit;

    @BeforeClass
    public static void createBrokers() {
        kafkaUnit = new KafkaUnit();
        kafkaUnit.setUpBrokers(1);
    }

    @AfterClass
    public static void destroyBrokers() {
        kafkaUnit.shutdown();
        kafkaUnit = null;
    }
}
