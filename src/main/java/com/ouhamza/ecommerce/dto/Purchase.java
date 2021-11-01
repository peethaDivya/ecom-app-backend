package com.ouhamza.ecommerce.dto;


import com.ouhamza.ecommerce.entity.Customer;
import com.ouhamza.ecommerce.entity.Order;
import com.ouhamza.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.List;


@Data
public class Purchase {
	private Customer customer;
    private Order order;
    private List<OrderItem> orderItems;
    }

