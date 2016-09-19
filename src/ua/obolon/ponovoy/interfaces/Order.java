/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.obolon.ponovoy.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import ua.obolon.ponovoy.res.ProductArt;

/**
 *
 * @author Alexander
 */
public interface Order {
    
    /**
     *
     * @return
     */
    long getId();
    Order setId(long id);
    String getTelephone();
    Order setTelephone(String telephone);
    List<Map<ProductArt,String>> getProducts();
    Order setProducts(List<Map<ProductArt,String>> products);
    Date getOrderDate();
    Order setOrderDate(Date date);
    String getStatus();
    Order setStatus(String status);
    String getPaymentType();
    Order setPaymentType(String paymentType);
    double getTotal();
    Order setTotal(double total);
    double getShipPrice();
    Order setShipPrice(double setshipPrice);
    String getShipMethod();
    Order setShipMethod(String shipMethod);
    String getFirstName();
    Order setFirstName(String firstName);
    String getLastName();
    Order setLastName(String lastName);
    String getAddress();
    Order setAddress(String address);
    String getEmail();
    Order setEmail(String email);
    
    
    
    
}
