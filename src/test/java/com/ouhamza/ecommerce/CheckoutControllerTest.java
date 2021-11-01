package com.ouhamza.ecommerce;

import com.ouhamza.ecommerce.beans.DbManager;
import com.ouhamza.ecommerce.beans.Users;
import com.ouhamza.ecommerce.dto.Purchase;
import com.ouhamza.ecommerce.entity.Customer;
import com.ouhamza.ecommerce.entity.Order;
import com.ouhamza.ecommerce.entity.OrderItem;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CheckoutControllerTest  extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createUser() throws Exception {
        String uri = "/api/checkout/purchase";
        Purchase purchase = new Purchase();

        Customer customer = new Customer();
        customer.setFirstName("divya");
        customer.setLastName("j");
        customer.setEmail("divyaj@gamail.com");
        customer.setAddress("bangalore");

        Order order =new Order();
        order.setStatus("available");
        order.setOrderTrackingNumber("bc196f1b-e5ca-4728-bf64-45cf6271dbe6");
        order.setTotalQuantity(2);
        order.setTotalPrice(BigDecimal.valueOf(78.00));

        ArrayList<OrderItem> list = new ArrayList<>();
        OrderItem orderItem= new OrderItem();
        orderItem.setName("Amul cheese");
        orderItem.setQuantity(3);
        orderItem.setPrice((float) 50.4);
        orderItem.setGroceryId(12L);
        list.add(orderItem);

        purchase.setOrder(order);
        purchase.setCustomer(customer);
        purchase.setOrderItems(list);

        String inputJson = super.mapToJson(purchase);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


}