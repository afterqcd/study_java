package com.afterqcd.study.kafka.serde;

import com.afterqcd.study.kafka.model.CustomerOuterClass;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import static org.junit.Assert.assertEquals;
import static com.afterqcd.study.kafka.serde.UnifySerdes.serde;
import org.junit.Test;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class UnifySerdesTest {
    @Test
    public void testGenerateSerdeForKafkaPredefinedClass() throws Exception {
        assertEquals(Serdes.Long().getClass(), serde(Long.class).getClass());
        assertEquals(Serdes.String().getClass(), serde(String.class).getClass());
        assertEquals(Serdes.Integer().getClass(), serde(Integer.class).getClass());
    }

    @Test
    public void testGenerateSerdeForProtobufMessageLiteSubClass() throws Exception {
        assertEquals(
                ProtoBufSerde.serde(CustomerOuterClass.Customer.class).getClass(),
                serde(CustomerOuterClass.Customer.class).getClass()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionForNonProtobufMessageLiteSubClass() throws Exception {
        serde(Some.class);
    }

    @Test
    public void testProtobufMessageSerDe() throws Exception {
        CustomerOuterClass.Customer zhangSan = CustomerOuterClass.Customer.newBuilder()
                .setName("zhang san")
                .setCity("chengdu")
                .setAge(15)
                .addInterests("game")
                .build();
        Serde<CustomerOuterClass.Customer> serde = serde(CustomerOuterClass.Customer.class);

        byte[] bytes = serde.serializer().serialize(null, zhangSan);
        CustomerOuterClass.Customer deserializedZhangSan = serde.deserializer().deserialize(null, bytes);
        assertEquals(zhangSan, deserializedZhangSan);
    }
}

class Some {}
