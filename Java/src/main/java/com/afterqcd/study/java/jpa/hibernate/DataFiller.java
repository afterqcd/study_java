package com.afterqcd.study.java.jpa.hibernate;

import com.afterqcd.study.java.jpa.hibernate.entity.Customer;
import com.afterqcd.study.java.jpa.hibernate.entity.Invoice;
import com.afterqcd.study.java.jpa.hibernate.entity.Order;
import com.afterqcd.study.java.jpa.hibernate.entity.OrderDetail;
import com.afterqcd.study.java.jpa.hibernate.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by afterqcd on 16/6/12.
 */
public class DataFiller {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Product macBookPro = new Product("MacBook Pro", 2100F);
        Product iPhone6s = new Product("iPhone6s", 690F);
        Product iPadAir = new Product("iPad Air", 490F);
        manager.persist(macBookPro);
        manager.persist(iPhone6s);
        manager.persist(iPadAir);

        // bob
        Customer bob = new Customer("bob", 15);
        manager.persist(bob);

        Order bobOrder = new Order(bob);
        manager.persist(bobOrder);

        OrderDetail mbp1 = new OrderDetail(bobOrder, macBookPro, 2050F, 1);
        OrderDetail iPhone6s1 = new OrderDetail(bobOrder, iPhone6s, 650F, 2);
        manager.persist(mbp1);
        manager.persist(iPhone6s1);

        Invoice bobInvoice = new Invoice(bobOrder);
        manager.persist(bobInvoice);

        // jimmy
        Customer jimmy = new Customer("jimmy", 20);
        manager.persist(jimmy);

        Order jimmyOrder = new Order(jimmy);
        manager.persist(jimmyOrder);

        OrderDetail mbp2 = new OrderDetail(jimmyOrder, macBookPro, 2050F, 1);
        OrderDetail iPadAir2 = new OrderDetail(jimmyOrder, iPhone6s, 450F, 3);
        manager.persist(mbp2);
        manager.persist(iPadAir2);

        Invoice jimmyInvoice = new Invoice(jimmyOrder);
        manager.persist(jimmyInvoice);

        manager.getTransaction().commit();
        manager.close();
        factory.close();
    }
}
