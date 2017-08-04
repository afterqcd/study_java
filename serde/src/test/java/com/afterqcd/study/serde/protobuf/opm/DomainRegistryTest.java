package com.afterqcd.study.serde.protobuf.opm;

import com.afterqcd.study.serde.protobuf.dto.Dto;
import com.afterqcd.study.serde.protobuf.model.Customer;
import com.afterqcd.study.serde.protobuf.model.Level;
import com.google.protobuf.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by afterqcd on 2016/12/23.
 */
public class DomainRegistryTest {
    private DomainRegistry registry;

    @Before
    public void setUp() throws Exception {
        registry = DomainRegistry.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        registry.cleanUp();
        registry = null;
    }

    @Test
    public void shouldSupportGetProtoByName() throws Exception {
        registry.registerClass(Customer.class);
        Class<? extends Message> protoCustomer
                = registry.getProtoClassByName(Dto.Customer.getDescriptor().getFullName());
        Assert.assertEquals(Dto.Customer.class, protoCustomer);
    }

    @Test
    public void shouldSupportProtoToDomain() throws Exception {
        registry.registerClass(Customer.class);
        Class<?> domainCustomer = registry.toDomainClass(Dto.Customer.class);
        Assert.assertEquals(Customer.class, domainCustomer);
    }

    @Test
    public void shouldSupportDomainToProtoEnum() throws Exception {
        registry.registerEnum(Level.class);
        Assert.assertEquals(Level.Debug, registry.toDomainEnum(Dto.Level.DEBUG));
        Assert.assertEquals(Level.Warn, registry.toDomainEnum(Dto.Level.WARN));
    }
}
