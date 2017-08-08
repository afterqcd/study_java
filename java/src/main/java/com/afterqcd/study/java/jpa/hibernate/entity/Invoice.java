package com.afterqcd.study.java.jpa.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by afterqcd on 16/6/12.
 */
@Entity
public class Invoice {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        Float amount = 0F;
        for (OrderDetail detail : order.getDetails()) {
            amount += detail.getAmount();
        }
        return amount;
    }

    public Invoice() {
    }

    public Invoice(Order order) {
        this.order = order;
    }
}
