package com.afterqcd.study.serde.protobuf.opm;

import com.afterqcd.study.serde.protobuf.dto.Dto;
import com.afterqcd.study.serde.protobuf.model.Attribute;
import com.afterqcd.study.serde.protobuf.model.Click;
import com.afterqcd.study.serde.protobuf.model.Context;
import com.afterqcd.study.serde.protobuf.model.Customer;
import com.afterqcd.study.serde.protobuf.model.Level;
import com.afterqcd.study.serde.protobuf.model.Log;
import com.afterqcd.study.serde.protobuf.model.NamedData;
import com.afterqcd.study.serde.protobuf.model.Square;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
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
    private DomainRegistry registry = DomainRegistry.getInstance();

    @Before
    public void setUp() throws Exception {
        converter = ConverterHolder.getConverter();
    }

    @After
    public void tearDown() throws Exception {
        converter = null;
        registry.cleanUp();
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
        // type parameter is bool
        NamedData<Boolean> namedBool = new NamedData<>();
        namedBool.setName("bool");
        namedBool.setData(true);

        Assert.assertEquals(namedBool, serDe(Dto.NamedData.class, namedBool));

        // type parameter is int
        NamedData<Integer> namedInt = new NamedData<>();
        namedInt.setName("int");
        namedInt.setData(15);

        Assert.assertEquals(namedInt, serDe(Dto.NamedData.class, namedInt));

        // type parameter is customized domain
        registry.registerClass(Customer.class);

        Customer customer = new Customer();
        customer.setName("zhang san");
        customer.setAge(15);
        NamedData<Customer> namedCustomer = new NamedData<>();
        namedCustomer.setName("customer");
        namedCustomer.setData(customer);

        Assert.assertEquals(namedCustomer, serDe(Dto.NamedData.class, namedCustomer));

        // generic in generic
        registry.registerClass(NamedData.class);
        NamedData<NamedData<Customer>> namedCustomer1 = new NamedData<>();
        namedCustomer1.setName("open me");
        namedCustomer1.setData(namedCustomer);

        Assert.assertEquals(namedCustomer1, serDe(Dto.NamedData.class, namedCustomer1));
    }

    @Test
    public void shouldSupportGenericPropertiesWithConcreteTypeParameter() throws Exception {
        Context context = new Context();
        NamedData<String> desc = new NamedData<>();
        desc.setName("desc");
        desc.setData("tenma");
        NamedData<Double> size = new NamedData<>();
        size.setName("size");
        size.setData(Double.MAX_VALUE);
        context.setDesc(desc);
        context.setSize(size);

        Assert.assertEquals(context, serDe(Dto.Context.class, context));
    }

    @Test
    public void shouldSupportSubclass() throws Exception {
        Square square = new Square();
        square.setName("square");
        square.setSideLength(1D);

        Assert.assertEquals(square, serDe(Dto.Square.class, square));
    }

    @Test
    public void shouldSupportEnumProperties() throws Exception {
        registry.registerEnum(Level.class);

        Log log = new Log();
        log.setLevel(Level.Warn);
        log.setMessage("a warn");

        Assert.assertEquals(log, serDe(Dto.Log.class, log));
    }

    @Test
    public void shouldSupportObjectProperties() throws Exception {
        Attribute sizeAttribute = new Attribute();
        sizeAttribute.setName("size");
        sizeAttribute.setValue(2D);
        Assert.assertEquals(sizeAttribute, serDe(Dto.Attribute.class, sizeAttribute));

        registry.registerClass(Customer.class);
        Customer customer = new Customer();
        customer.setName("zhang san");
        customer.setAge(15);
        Attribute customerAttribute = new Attribute();
        customerAttribute.setName("customer");
        customerAttribute.setValue(customer);
        Assert.assertEquals(customerAttribute, serDe(Dto.Attribute.class, customerAttribute));
    }
}
