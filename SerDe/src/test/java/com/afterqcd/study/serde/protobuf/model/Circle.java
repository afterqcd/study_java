package com.afterqcd.study.serde.protobuf.model;

import com.afterqcd.study.serde.protobuf.dto.Dto;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

/**
 * Created by afterqcd on 2016/12/21.
 */
@ProtoClass(Dto.Circle.class)
public class Circle extends Shape {
    @ProtoField
    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
