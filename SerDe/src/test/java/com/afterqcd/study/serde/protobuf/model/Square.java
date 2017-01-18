package com.afterqcd.study.serde.protobuf.model;

import com.afterqcd.study.serde.protobuf.dto.Dto;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

/**
 * Created by afterqcd on 2016/12/21.
 */
@ProtoClass(Dto.Square.class)
public class Square extends Shape {
    @ProtoField
    private double sideLength;

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }
}
