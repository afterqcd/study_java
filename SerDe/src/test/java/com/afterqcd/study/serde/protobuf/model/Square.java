package com.afterqcd.study.serde.protobuf.model;

/**
 * Created by afterqcd on 2016/12/21.
 */
public class Square extends Shape {
    private double sideLength;

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }
}
