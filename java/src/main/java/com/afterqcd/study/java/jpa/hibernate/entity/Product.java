package com.afterqcd.study.java.jpa.hibernate.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Created by afterqcd on 16/6/12.
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Float regularPrice;

    @OneToMany(mappedBy = "product", targetEntity = OrderDetail.class)
    private List<OrderDetail> orderDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Float regularPrice) {
        this.regularPrice = regularPrice;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Product() {
    }

    public Product(String name, Float regularPrice) {
        this.name = name;
        this.regularPrice = regularPrice;
    }
}
