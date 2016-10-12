/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.magento.caller.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import ua.magento.caller.interfaces.Order;
import ua.magento.caller.res.ProductArt;

/**
 *
 * @author Alexander
 */
public class OrderImpl implements Order, Serializable {

    private long id;
    private String orderID;
    private List<Map<ProductArt, String>> products;
    private Date orderDate;
    private String status;
    private String paymentType;
    private double total;
    private double shipPrice;
    private String shipMethod;
    private String address;
    private String firstname, lastname;
    private String email;
    private String telephone;

    @Override
    public String getOrderID() {
        return orderID;
    }

    @Override
    public Order setOrderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    @Override
    public List<Map<ProductArt, String>> getProducts() {
        return products;
    }

    @Override
    public Order setProducts(List<Map<ProductArt, String>> products) {
        this.products = products;
        return this;
    }

    @Override
    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getPaymentType() {
        return paymentType;
    }

    @Override
    public double getTotal() {
        return total;
    }

    @Override
    public Order setTotal(double total) {
        this.total = total;
        return this;
    }

    @Override
    public double getShipPrice() {
        return shipPrice;
    }

    @Override
    public Order setShipPrice(double setshipPrice) {
        this.shipPrice = setshipPrice;
        return this;
    }

    @Override
    public String getShipMethod() {
        return shipMethod;
    }

    @Override
    public Order setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Order setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Order setOrderDate(Date date) {
        this.orderDate = date;
        return this;
    }

    @Override
    public Order setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public Order setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    @Override
    public String getFirstName() {
        return firstname;
    }

    @Override
    public Order setFirstName(String firstName) {
        this.firstname = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return lastname;
    }

    @Override
    public Order setLastName(String lastName) {
        this.lastname = lastName;
        return this;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Order setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getTelephone() {
        return telephone;
    }

    @Override
    public Order setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderImpl other = (OrderImpl) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
