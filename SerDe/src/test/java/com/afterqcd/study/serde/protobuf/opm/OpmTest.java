package com.afterqcd.study.serde.protobuf.opm;

import com.afterqcd.study.serde.protobuf.dto.Dto;
import com.afterqcd.study.serde.protobuf.model.Click;
import com.afterqcd.study.serde.protobuf.model.Customer;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import net.badata.protobuf.converter.Converter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by afterqcd on 2016/12/22.
 */
public class OpmTest {
    private Converter converter;

    @Before
    public void setUp() throws Exception {
        converter = Converter.create();
    }

    @After
    public void tearDown() throws Exception {
        converter = null;
    }

    @Test
    public void shouldSupportPrimitiveProperties() throws Exception {
        Customer customer = new Customer();
        customer.setName("zhang san");
        customer.setAge(15);

        Assert.assertEquals(customer, serDe(Dto.Customer.class, customer));
    }

    @SuppressWarnings("unchecked")
    private <D, P extends Message> D serDe(Class<P> protoClz, D domain) {
        P proto = converter.toProtobuf(protoClz, domain);
        byte[] bytes = proto.toByteArray();
        Parser<? extends Message> parser = proto.getParserForType();
        try {
            P proto1 = (P) parser.parseFrom(bytes);
            return (D) converter.toDomain(domain.getClass(), proto1);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("Failed to parse bytes " + e.getMessage(), e);
        }
    }

    @Test
    public void shouldSupportConcreteObjectProperties() throws Exception {
        Customer customer = new Customer();
        customer.setName("zhang san");
        customer.setAge(15);

        Click click = new Click();
        click.setCustomer(customer);
        click.setTime(new Date());
        click.setWhat("ha ha");

        Assert.assertEquals(click, serDe(Dto.Click.class, click));
    }

    @Test
    public void shouldSupportGenericClass() throws Exception {
        // NamedData

    }

    @Test
    public void shouldSupportGenericPropertiesWithConcreteTypeParameter() throws Exception {
        // Context
    }

    @Test
    public void shouldSupportSubclass() throws Exception {
        // Square and Circle
    }

    @Test
    public void shouldSupportEnumProperties() throws Exception {
        // Log

    }

    @Test
    public void shouldSupportObjectProperties() throws Exception {
        // Attribute
    }
}
