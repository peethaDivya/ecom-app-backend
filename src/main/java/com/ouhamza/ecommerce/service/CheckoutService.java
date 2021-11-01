package com.ouhamza.ecommerce.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ouhamza.ecommerce.dto.Purchase;
import com.ouhamza.ecommerce.dto.PurchaseResponse;
import com.ouhamza.ecommerce.entity.Customer;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
    
//    @Query(value = "SELECT * FROM customer JOIN orders ON customer.id = orders.customer_id JOIN order_item \r\n"
//    		+ "ON order_item.order_id = orders.customer_id where customer.id =:id",
//            nativeQuery = true)
//    List<Customer> queryBy(@Param("id") int id);
}
