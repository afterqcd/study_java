package com.afterqcd.study.serde.protobuf.model;

import com.afterqcd.study.serde.protobuf.dto.Dto;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.type.DateLongConverterImpl;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Date;

/**
 * Created by afterqcd on 2016/12/21.
 */
@ProtoClass(Dto.Click.class)
public class Click {
    @ProtoField
    private Customer customer;
    @ProtoField(converter = DateLongConverterImpl.class)
    private Date time;
    @ProtoField
    private String what;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
