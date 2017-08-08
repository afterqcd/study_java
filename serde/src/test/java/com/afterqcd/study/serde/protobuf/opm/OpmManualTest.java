package com.afterqcd.study.serde.protobuf.opm;

import avro.shaded.com.google.common.collect.Maps;
import com.afterqcd.study.serde.protobuf.dto.Dto;
import com.afterqcd.study.serde.protobuf.model.Attribute;
import com.afterqcd.study.serde.protobuf.model.Click;
import com.afterqcd.study.serde.protobuf.model.Context;
import com.afterqcd.study.serde.protobuf.model.Customer;
import com.afterqcd.study.serde.protobuf.model.Level;
import com.afterqcd.study.serde.protobuf.model.Log;
import com.afterqcd.study.serde.protobuf.model.NamedData;
import com.afterqcd.study.serde.protobuf.model.Square;
import com.google.protobuf.Any;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * Created by afterqcd on 2016/12/21.
 */
public class OpmManualTest {
    @Test
    public void shouldSupportPrimitiveProperties() throws Exception {
        // 序列化
        Customer customer = new Customer();
        customer.setAge(15);
        customer.setName("zhang san");
        Dto.Customer customerDto = Dto.Customer.newBuilder()
                .setAge(customer.getAge())
                .setName(customer.getName())
                .build();

        byte[] bytes = customerDto.toByteArray();

        // 反序列化
        Dto.Customer customerDto1 = Dto.Customer.parseFrom(bytes);
        Customer customer1 = new Customer();
        customer1.setAge(customerDto1.getAge());
        customer1.setName(customerDto1.getName());

        Assert.assertEquals(customer, customer1);
    }

    @Test
    public void shouldSupportConcreteObjectProperties() throws Exception {
        // 序列化
        Customer customer = new Customer();
        customer.setAge(15);
        customer.setName("zhang san");
        Click click = new Click();
        click.setCustomer(customer);
        click.setTime(new Date());
        click.setWhat("ha ha");
        Dto.Click clickDto = Dto.Click.newBuilder()
                .setCustomer(Dto.Customer.newBuilder()
                        .setAge(customer.getAge())
                        .setName(customer.getName()))
                .setTime(click.getTime().getTime())
                .setWhat(click.getWhat())
                .build();

        byte[] bytes = clickDto.toByteArray();

        // 反序列化
        Dto.Click clickDto1 = Dto.Click.parseFrom(bytes);
        Customer customer1 = new Customer();
        customer1.setAge(clickDto1.getCustomer().getAge());
        customer1.setName(clickDto1.getCustomer().getName());
        Click click1 = new Click();
        click1.setCustomer(customer);
        click1.setTime(new Date(clickDto1.getTime()));
        click1.setWhat(clickDto1.getWhat());

        Assert.assertEquals(click, click1);
    }

    @Test
    public void shouldSupportGenericClass() throws Exception {
        // int
        NamedData<Integer> namedInt = new NamedData<>();
        namedInt.setName("int");
        namedInt.setData(123);

        Dto.NamedData namedIntDto = Dto.NamedData.newBuilder()
                .setName(namedInt.getName())
                .setData(Dto.Generic.newBuilder().setInt(namedInt.getData()))
                .build();

        byte[] intBytes = namedIntDto.toByteArray();

        Dto.NamedData namedDataDto1 = Dto.NamedData.parseFrom(intBytes);
        NamedData<Integer> namedInt1 = new NamedData<>();
        namedInt1.setName(namedDataDto1.getName());
        namedInt1.setData(namedDataDto1.getData().getInt());

        Assert.assertEquals(Dto.Generic.ValueCase.INT, namedDataDto1.getData().getValueCase());
        Assert.assertEquals(namedInt, namedInt1);

        // Customer
        Customer customer = new Customer();
        customer.setAge(15);
        customer.setName("zhang san");
        NamedData<Customer> namedCustomer = new NamedData<>();
        namedCustomer.setName("zhang san");
        namedCustomer.setData(customer);

        Dto.NamedData namedCustomerDto = Dto.NamedData.newBuilder()
                .setName(namedCustomer.getName())
                .setData(Dto.Generic.newBuilder().setAny(Any.pack(
                        Dto.Customer.newBuilder()
                                .setAge(namedCustomer.getData().getAge())
                                .setName(namedCustomer.getData().getName()).build()
                ))).build();

        byte[] customerBytes = namedCustomerDto.toByteArray();

        Dto.NamedData nameCustomerDto1 = Dto.NamedData.parseFrom(customerBytes);
        NamedData<Customer> namedCustomer1 = new NamedData<>();
        namedCustomer1.setName(nameCustomerDto1.getName());
        Customer customer1 = new Customer();
        customer1.setAge(nameCustomerDto1.getData().getAny().unpack(Dto.Customer.class).getAge());
        customer1.setName(nameCustomerDto1.getData().getAny().unpack(Dto.Customer.class).getName());
        namedCustomer1.setData(customer1);

        Assert.assertEquals(Dto.Generic.ValueCase.ANY, nameCustomerDto1.getData().getValueCase());
        Assert.assertEquals(namedCustomer, namedCustomer1);
    }

    @Test
    public void shouldSupportGenericPropertiesWithConcreteTypeParameter() throws Exception {
        // 序列化
        Context context = new Context();
        NamedData<String> desc = new NamedData<>();
        desc.setName("desc");
        desc.setData("tenma");
        NamedData<Double> size = new NamedData<>();
        size.setName("size");
        size.setData(Double.MAX_VALUE);
        context.setDesc(desc);
        context.setSize(size);

        Dto.Context contextDto = Dto.Context.newBuilder()
                .setDesc(Dto.NamedData.newBuilder()
                        .setName(desc.getName())
                        .setData(Dto.Generic.newBuilder().setString(desc.getData())))
                .setSize(Dto.NamedData.newBuilder()
                        .setName(size.getName())
                        .setData(Dto.Generic.newBuilder().setDouble(size.getData())))
                .build();

        byte[] bytes = contextDto.toByteArray();

        // 反序列化
        Dto.Context contextDto1 = Dto.Context.parseFrom(bytes);
        Context context1 = new Context();
        NamedData<String> desc1 = new NamedData<>();
        desc1.setName(contextDto1.getDesc().getName());
        Assert.assertEquals(Dto.Generic.ValueCase.STRING, contextDto1.getDesc().getData().getValueCase());
        desc1.setData(contextDto1.getDesc().getData().getString());
        context1.setDesc(desc1);
        NamedData<Double> size1 = new NamedData<>();
        size1.setName(contextDto1.getSize().getName());
        Assert.assertEquals(Dto.Generic.ValueCase.DOUBLE, contextDto1.getSize().getData().getValueCase());
        size1.setData(contextDto1.getSize().getData().getDouble());
        context1.setSize(size1);

        Assert.assertEquals(context, context1);
    }

    @Test
    public void shouldSupportEnum() throws Exception {
        Level level = Level.Warn;
        Dto.Level levelDto = Dto.Level.forNumber(level.ordinal());

        // 不支持独立序列化枚举
    }

    @Test
    public void shouldSupportSubclass() throws Exception {
        Square square = new Square();
        square.setName("square");
        square.setSideLength(1D);

        Dto.Square squareDto = Dto.Square.newBuilder().setName(square.getName()).setSideLength(square.getSideLength()).build();
        Any any = Any.pack(squareDto);
        byte[] squareBytes = any.toByteArray();
        Any any1 = Any.parseFrom(squareBytes);
        Assert.assertTrue(any.is(Dto.Square.class));

        Dto.Square squareDto1 = any1.unpack(Dto.Square.class);
        Square square1 = new Square();
        square1.setName(squareDto1.getName());
        square1.setSideLength(squareDto1.getSideLength());

        Assert.assertEquals(square, square1);
    }

    @Test
    public void shouldSupportEnumProperties() throws Exception {
        Map<Integer, Level> levels = Maps.newHashMap();
        for (Level level : Level.values()) {
            levels.put(level.ordinal(), level);
        }

        // 序列化
        Log log = new Log();
        log.setLevel(Level.Warn);
        log.setMessage("warning");

        Dto.Log logDto = Dto.Log.newBuilder()
                .setMessage(log.getMessage())
                .setLevel(Dto.Level.forNumber(log.getLevel().ordinal()))
                .build();

        byte[] bytes = logDto.toByteArray();

        // 反序列化
        Dto.Log logDto1 = Dto.Log.parseFrom(bytes);
        Log log1 = new Log();
        log1.setMessage(logDto1.getMessage());
        log1.setLevel(levels.get(logDto1.getLevel().getNumber()));

        Assert.assertEquals(log, log1);
    }

    @Test
    public void shouldSupportObjectProperties() throws Exception {
        // 序列化
        Attribute attribute = new Attribute();
        attribute.setName("size");
        attribute.setValue(Double.MAX_VALUE);
        Dto.Attribute attributeDto = Dto.Attribute.newBuilder()
                .setName(attribute.getName())
                .setValue(Dto.Generic.newBuilder().setDouble((Double) attribute.getValue()))
                .build();

        byte[] bytes = attributeDto.toByteArray();

        // 反序列化
        Dto.Attribute attributeDto1 = Dto.Attribute.parseFrom(bytes);
        Attribute attribute1 = new Attribute();
        attribute1.setName(attributeDto1.getName());
        Assert.assertEquals(Dto.Generic.ValueCase.DOUBLE, attributeDto1.getValue().getValueCase());
        attribute1.setValue(attributeDto1.getValue().getDouble());

        Assert.assertEquals(attribute, attribute1);
    }
}
