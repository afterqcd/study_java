package com.afterqcd.study.kafka.serde;

import com.afterqcd.study.kafka.model.CustomerOuterClass;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import static org.junit.Assert.assertEquals;
import static com.afterqcd.study.kafka.serde.UnifySerdes.serdeFrom;
import org.junit.Test;

/**
 * Created by afterqcd on 2016/12/6.
 */
public class UnifySerdesTest {
    @Test
    public void testGenerateSerdeForKafkaPredefinedClass() throws Exception {
        assertEquals(Serdes.Long().getClass(), serdeFrom(Long.class).getClass());
        assertEquals(Serdes.String().getClass(), serdeFrom(String.class).getClass());
        assertEquals(Serdes.Integer().getClass(), serdeFrom(Integer.class).getClass());
    }

    @Test
    public void testGenerateSerdeForProtobufMessageLiteSubClass() throws Exception {
        assertEquals(
                ProtoBufSerde.serde(CustomerOuterClass.Customer.class).getClass(),
                serdeFrom(CustomerOuterClass.Customer.class).getClass()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionForNonProtobufMessageLiteSubClass() throws Exception {
        serdeFrom(Some.class);
    }

    @Test
    public void testProtobufMessageSerDe() throws Exception {
        CustomerOuterClass.Customer zhangSan = CustomerOuterClass.Customer.newBuilder()
                .setName("zhang san")
                .setCity("chengdu")
                .setAge(15)
                .addInterests("game")
                .build();
        Serde<CustomerOuterClass.Customer> serde = serdeFrom(CustomerOuterClass.Customer.class);

        byte[] bytes = serde.serializer().serialize(null, zhangSan);
        CustomerOuterClass.Customer deserializedZhangSan = serde.deserializer().deserialize(null, bytes);
        assertEquals(zhangSan, deserializedZhangSan);
    }
}

class Some {}
