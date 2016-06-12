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
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer age;

    @OneToMany(mappedBy = "customer", targetEntity = Order.class)
    private List<Order> orders;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Customer() {
    }

    public Customer(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
