package com.afterqcd.study.java.jpa.hibernate;

import com.afterqcd.study.java.jpa.hibernate.entity.Customer;
import com.afterqcd.study.java.jpa.hibernate.entity.Order;
import com.afterqcd.study.java.jpa.hibernate.entity.OrderDetail;
import com.afterqcd.study.java.jpa.hibernate.entity.Product;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by afterqcd on 16/6/12.
 */
public class Querier {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        parseRelationship(manager);
        jpql(manager);

        manager.getTransaction().commit();
        manager.close();
        factory.close();
    }

    private static void parseRelationship(EntityManager manager) {
        Customer customer = manager.find(Customer.class, 4L);
        List<Order> orders = customer.getOrders();
        for (Order order : orders) {
            System.out.println("Purchased at " + order.getOrderDate());
            for (OrderDetail detail : order.getDetails()) {
                Product product = detail.getProduct();
                System.out.println("\t" + product.getName() + ", quantity: " + detail.getQuantity() + ", price: " +detail.getPrice());
            }
            System.out.println("\tTotal: " + order.getInvoice().getAmount());
        }
    }

    private static void jpql(EntityManager manager) {
        List<Customer> customers =
                (List<Customer>) manager.createQuery("SELECT c FROM Customer c").getResultList();
        for (Customer c : customers) {
            System.out.println(c.getName());
        }
    }
}
