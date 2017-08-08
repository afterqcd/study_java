package com.afterqcd.study.java.jpa.hibernate.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by afterqcd on 16/6/12.
 */
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private Date orderDate;

    @OneToOne(mappedBy = "order", targetEntity = Invoice.class)
    private Invoice invoice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "order", targetEntity = OrderDetail.class)
    private List<OrderDetail> details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public Order() {
    }

    public Order(Customer customer) {
        this.customer = customer;
        this.orderDate = new Date();
    }
}
